package com.android.awamp.view.util

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.android.awamp.view.util.row.Row

abstract class VH(view: View): RecyclerView.ViewHolder(view) {
    abstract fun bind(item: Row)
}
