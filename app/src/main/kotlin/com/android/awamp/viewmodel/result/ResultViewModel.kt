package com.android.awamp.viewmodel.result

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.android.awamp.R
import com.android.awamp.domain.MainInteractor
import com.android.awamp.domain.Side
import com.android.awamp.storage.SharedPreferenceStorage
import com.android.awamp.storage.SpKeys
import com.android.awamp.viewmodel.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ResultViewModel(
    private val context: Context,
    private val interactor: MainInteractor,
    private val sharedPreferenceStorage: SharedPreferenceStorage,
) : BaseViewModel() {

    private val _state = MutableStateFlow(
        ResultUiState(
            weight = "",
            dose = "",
            fields = emptyList()
        )
    )
    val state: StateFlow<ResultUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            interactor.getFields()
                .map { fields ->
                    fields.filter { it.dose != null && it.dose != 0f }
                }
                .collect { fields ->
                    _state.value = ResultUiState(
                        weight = sharedPreferenceStorage.getValue(SpKeys.WEIGHT_KEY, 0f).toString(),
                        dose = sharedPreferenceStorage.getValue(SpKeys.CURRENT_DOSE, 0f).toString(),
                        fields = fields.mapIndexed { index, field ->
                            ResultTableField(
                                index = index + 1,
                                name = field.name,
                                side = getSideText(field.side),
                                dose = field.dose?.toString().orEmpty()
                            )
                        }
                    )
                }
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
