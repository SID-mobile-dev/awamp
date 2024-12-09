package com.android.awamp.view.util.row

import com.android.awamp.domain.Side

data class CheckedFieldRow(
    val hint: String,
    val checkedDataList: List<CheckedFieldCheckboxData>,
    val onClick: () -> Unit,
) : Row

data class CheckedFieldCheckboxData(
    val isChecked: Boolean,
    val side: Side,
)

data class InfoFieldRow(
    val hint: String,
    val value: String,
    val errorText: String? = null,
) : Row

data class HeaderRow(
    val value: String,
): Row
