package com.android.awamp.viewmodel.result

data class ResultUiState(
    val weight: String,
    val dose: String,
    val fields: List<ResultTableField>,
)

data class ResultTableField(
    val index: Int,
    val name: String,
    val side: String,
    val dose: String,
)
