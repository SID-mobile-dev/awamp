package com.android.awamp.viewmodel.main

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.android.awamp.R
import com.android.awamp.domain.BodyPart
import com.android.awamp.domain.Field
import com.android.awamp.domain.MainInteractor
import com.android.awamp.storage.SharedPreferenceStorage
import com.android.awamp.storage.SpKeys
import com.android.awamp.view.main.MainFragmentDirections
import com.android.awamp.view.util.row.CheckedFieldCheckboxData
import com.android.awamp.view.util.row.CheckedFieldRow
import com.android.awamp.view.util.row.HeaderRow
import com.android.awamp.view.util.row.InfoFieldRow
import com.android.awamp.view.util.row.Row
import com.android.awamp.viewmodel.base.BaseViewModel
import com.android.awamp.viewmodel.base.NavigationCommand
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.math.min

class MainViewModel(
    private val context: Context,
    private val preferenceStorage: SharedPreferenceStorage,
    private val interactor: MainInteractor,
) : BaseViewModel() {

    companion object {

        private const val MAX_DOSE = 400f
        private const val MAX_DOSE_MULTIPLIER = 16
    }

    private val _state = MutableStateFlow(MainUiState.empty())
    val state: StateFlow<MainUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            interactor.getFields().map { list ->
                createRows(list.groupBy { it.name })
            }.collect { rows ->
                val weight = preferenceStorage.getValue(SpKeys.WEIGHT_KEY, 0f)
                val currentDose = preferenceStorage.getValue(SpKeys.CURRENT_DOSE, 0f)
                val maxDose = min(MAX_DOSE_MULTIPLIER * weight, MAX_DOSE)
                _state.value = MainUiState(
                    rows = rows,
                    isActionEnabled = currentDose != 0f && currentDose <= maxDose,
                )
            }
        }
    }

    fun onResultCLicked() {
        navigate(
            NavigationCommand.ToDest(
                MainFragmentDirections.actionMainFragmentToResultFragment()
            )
        )
    }

    private fun createRows(fieldsMap: Map<String, List<Field>>): List<Row> {
        val rows = mutableListOf<Row>()
        val weight = preferenceStorage.getValue(SpKeys.WEIGHT_KEY, 0f)
        val currentDose = preferenceStorage.getValue(SpKeys.CURRENT_DOSE, 0f)
        val maxDose = min(MAX_DOSE_MULTIPLIER * weight, MAX_DOSE)

        rows.add(
            InfoFieldRow(
                hint = context.resources.getString(R.string.maximum_dose),
                value = maxDose.toInt().toString(),
            )
        )
        rows.add(
            InfoFieldRow(hint = context.resources.getString(R.string.current_dose),
                value = currentDose.toInt().toString(),
                errorText = context.resources.getString(R.string.exceeded_maximum_dose)
                    .takeIf { currentDose > maxDose })
        )

        rows.add(
            HeaderRow(
                value = context.resources.getString(R.string.upper_limbs_title)
            )
        )
        rows.addAll(createTopRows(fieldsMap))

        rows.add(
            HeaderRow(
                value = context.resources.getString(R.string.lower_limbs_title)
            )
        )

        rows.addAll(createBottomRows(fieldsMap))

        return rows.toList()
    }

    private fun createTopRows(fieldsMap: Map<String, List<Field>>): List<Row> {
        return fieldsMap.filterValues { list ->
            list.all { it.bodyPart == BodyPart.TOP }
        }.map { field ->
            CheckedFieldRow(hint = field.key, checkedDataList = field.value.map {
                CheckedFieldCheckboxData(
                    isChecked = it.dose != null && it.dose != 0f, side = it.side
                )
            }, onClick = {
                navigate(
                    NavigationCommand.ToDest(
                        MainFragmentDirections.actionMainFragmentToChooseSideFragment(
                            field.key
                        )
                    )
                )
            })
        }
    }

    private fun createBottomRows(fieldsMap: Map<String, List<Field>>): List<Row> {
        return fieldsMap.filterValues { list ->
            list.all { it.bodyPart == BodyPart.BOTTOM }
        }.map { field ->
            CheckedFieldRow(hint = field.key, checkedDataList = field.value.map {
                CheckedFieldCheckboxData(
                    isChecked = it.dose != null && it.dose != 0f, side = it.side
                )
            }, onClick = {
                navigate(
                    NavigationCommand.ToDest(
                        MainFragmentDirections.actionMainFragmentToChooseSideFragment(
                            field.key
                        )
                    )
                )
            })
        }
    }
}
