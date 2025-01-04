package com.android.awamp.viewmodel.input

data class InputUiState(
    val toolbarTitle: String,
    val sideText: String,
    val minDose: Float,
    val maxDose: Float,
    val dose: Float?,
)
