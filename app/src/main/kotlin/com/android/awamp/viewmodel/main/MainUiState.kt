package com.android.awamp.viewmodel.main

import com.android.awamp.view.util.row.Row

data class MainUiState(
    val rows: List<Row>,
    val isActionEnabled: Boolean,
) {

    companion object {

        fun empty() = MainUiState(
            rows = emptyList(),
            isActionEnabled = false
        )
    }
}
