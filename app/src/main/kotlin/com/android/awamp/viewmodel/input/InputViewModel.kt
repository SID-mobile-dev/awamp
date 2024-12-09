package com.android.awamp.viewmodel.input

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.awamp.R
import com.android.awamp.domain.MainInteractor
import com.android.awamp.domain.Side
import com.android.awamp.storage.SharedPreferenceStorage
import com.android.awamp.storage.SpKeys
import com.android.awamp.view.input.InputFragmentArgs
import com.android.awamp.view.input.InputFragmentDirections
import com.android.awamp.viewmodel.base.BaseViewModel
import com.android.awamp.viewmodel.base.NavigationCommand
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class InputViewModel(
    private val context: Context,
    private val interactor: MainInteractor,
    private val sharedPreferenceStorage: SharedPreferenceStorage,
    private val args: InputFragmentArgs,
) : BaseViewModel() {

    private val _state = MutableStateFlow(
        InputUiState(
            toolbarTitle = args.name,
            sideText = getSideText(args.side),
            interval = "",
            minDose = 0.0f,
            maxDose = 0.0f,
            dose = null,
        )
    )
    val state: StateFlow<InputUiState> = _state.asStateFlow()

    private val _errorLiveData = MutableLiveData<ErrorState>()
    val errorLiveData: LiveData<ErrorState> = _errorLiveData

    init {
        viewModelScope.launch {
            val weight = sharedPreferenceStorage.getValue(SpKeys.WEIGHT_KEY, 0.0f)
            interactor.getField(args.name, args.side)
                .collect { field ->
                    _state.value = InputUiState(
                        toolbarTitle = field.name,
                        sideText = getSideText(field.side),
                        interval = "${field.intervalStart} — ${field.intervalEnd}",
                        minDose = (weight * field.intervalStart * 100).toInt().toFloat() / 100,
                        maxDose = (weight * field.intervalEnd * 100).toInt().toFloat() / 100,
                        dose = field.dose,
                    )
                }
        }
    }

    fun validateValue(value: String?) {
        val cache = _state.value
        when {
            value.isNullOrEmpty() -> {
                _errorLiveData.postValue(ErrorState(false, null))
            }

            value.toDoubleOrNull() == null -> {
                _errorLiveData.postValue(
                    ErrorState(
                        hasError = true,
                        errorText = context.resources.getString(R.string.input_incorrect)
                    )
                )
            }

            value.toDouble() == 0.0 -> {
                _errorLiveData.postValue(
                    ErrorState(false, null)
                )
            }

            value.toDouble() < cache.minDose -> {
                _errorLiveData.postValue(
                    ErrorState(
                        hasError = true,
                        errorText = context.resources.getString(R.string.input_incorrect_too_low)
                    )
                )
            }

            value.toDouble() > cache.maxDose -> {
                _errorLiveData.postValue(
                    ErrorState(
                        hasError = true,
                        errorText = context.resources.getString(R.string.input_incorrect_too_big)
                    )
                )
            }

            else -> {
                _errorLiveData.postValue(
                    ErrorState(false, null)
                )
            }
        }
    }

    fun setDose(dose: String?) {
        viewModelScope.launch {
            val field = interactor.getField(args.name, args.side).first()
            dose?.toFloatOrNull()?.let { dose ->
                val realDose = if (field.dose != null && field.dose != 0f) {
                    dose - field.dose
                } else {
                    dose
                }
                val currentDose = sharedPreferenceStorage.getValue(SpKeys.CURRENT_DOSE, 0f)
                sharedPreferenceStorage.putValue(SpKeys.CURRENT_DOSE, currentDose + realDose)
            }
            interactor.updateField(field, dose?.toFloatOrNull())
            navigate(
                NavigationCommand.ToDest(
                    InputFragmentDirections.actionInputFragmentToMainFragment()
                )
            )
        }
    }

    private fun getSideText(side: Side): String {
        return context.resources.getString(
            when (side) {
                Side.LEFT -> R.string.left
                Side.RIGHT -> R.string.right
            }
        )
    }
}
