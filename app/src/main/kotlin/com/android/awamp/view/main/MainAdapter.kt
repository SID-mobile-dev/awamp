package com.android.awamp.view.main

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.awamp.R
import com.android.awamp.databinding.ItemCheckedFieldBinding
import com.android.awamp.databinding.ItemHeaderBinding
import com.android.awamp.databinding.ItemInfoFieldBinding
import com.android.awamp.domain.Side
import com.android.awamp.util.viewBinding
import com.android.awamp.view.util.VH
import com.android.awamp.view.util.row.CheckedFieldRow
import com.android.awamp.view.util.row.HeaderRow
import com.android.awamp.view.util.row.InfoFieldRow
import com.android.awamp.view.util.row.Row

class MainAdapter : RecyclerView.Adapter<VH>() {

    companion object {

        private const val VIEW_TYPE_CHECKED_FIELD = 0
        private const val VIEW_TYPE_INFO_FIELD = 1
        private const val VIEW_TYPE_HEADER = 2
    }

    private val items = mutableListOf<Row>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return when (viewType) {
            VIEW_TYPE_CHECKED_FIELD -> {
                CheckedFieldViewHolder(parent.viewBinding(ItemCheckedFieldBinding::inflate))
            }

            VIEW_TYPE_INFO_FIELD -> {
                InfoFieldViewHolder(parent.viewBinding(ItemInfoFieldBinding::inflate))
            }

            VIEW_TYPE_HEADER -> {
                HeaderViewHolder(parent.viewBinding(ItemHeaderBinding::inflate))
            }

            else -> throw IllegalArgumentException("ViewHolder type does not exist")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is CheckedFieldRow -> VIEW_TYPE_CHECKED_FIELD
            is InfoFieldRow -> VIEW_TYPE_INFO_FIELD
            is HeaderRow -> VIEW_TYPE_HEADER
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        when (holder) {
            is CheckedFieldViewHolder -> holder.bind(item)
            is InfoFieldViewHolder -> holder.bind(item)
            is HeaderViewHolder -> holder.bind(item)
        }
    }

    fun setData(list: List<Row>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    inner class CheckedFieldViewHolder(
        private val binding: ItemCheckedFieldBinding
    ) : VH(binding.root) {

        private var onClicked: (() -> Unit)? = null

        init {
            binding.root.setOnClickListener {
                onClicked?.invoke()
            }
        }

        override fun bind(item: Row) {
            item as CheckedFieldRow
            val anyIsChecked = item.checkedDataList.any { it.isChecked }
            with(binding) {
                checkedFieldTextInput.hint = item.hint
                ContextCompat.getColorStateList(
                    checkedFieldTextInput.context,
                    if (anyIsChecked) {
                        R.color.text_input_success
                    } else {
                        R.color.text_input_default
                    }
                )?.let {
                    checkedFieldTextInput.defaultHintTextColor = it
                    checkedFieldTextInput.setBoxStrokeColorStateList(it)
                }
                item.checkedDataList.forEach { data ->
                    when (data.side) {
                        Side.LEFT -> checkedFieldCheckboxLeft.isChecked = data.isChecked
                        Side.RIGHT -> checkedFieldCheckboxRight.isChecked = data.isChecked
                    }
                }

                onClicked = item.onClick
            }
        }
    }

    inner class InfoFieldViewHolder(
        private val binding: ItemInfoFieldBinding,
    ) : VH(binding.root) {

        override fun bind(item: Row) {
            item as InfoFieldRow
            with(binding) {
                infoFieldTextInput.hint = item.hint
                infoFieldTextInput.error = item.errorText
                infoFieldEditText.setText(item.value)
            }
        }
    }

    inner class HeaderViewHolder(
        private val binding: ItemHeaderBinding,
    ) : VH(binding.root) {

        override fun bind(item: Row) {
            item as HeaderRow
            binding.itemHeader.text = item.value
        }
    }
}
