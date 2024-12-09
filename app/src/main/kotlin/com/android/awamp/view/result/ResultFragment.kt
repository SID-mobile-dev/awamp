package com.android.awamp.view.result

import android.os.Bundle
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.setPadding
import com.android.awamp.R
import com.android.awamp.databinding.ScreenResultBinding
import com.android.awamp.util.dpToPx
import com.android.awamp.util.observe
import com.android.awamp.util.viewBinding
import com.android.awamp.view.base.BaseFragment
import com.android.awamp.viewmodel.result.ResultViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ResultFragment : BaseFragment(R.layout.screen_result) {

    private val binding by viewBinding(ScreenResultBinding::bind)

    override val viewModel: ResultViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbar(binding.resultToolbar)
        observeData()
    }

    private fun observeData() {
        observe(viewModel.state) { state ->
            with(binding) {
                resultWeightEditText.setText(state.weight)
                resultCurrentDoseEditText.setText(state.dose)

                resultTable.children.forEachIndexed { index, view ->
                    if (index != 0) {
                        resultTable.removeViewAt(index)
                    }
                }
                state.fields.forEach { field ->
                    val row = TableRow(requireContext())
                    row.layoutParams = TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT
                    )
                    row.setPadding(8.dpToPx())
                    row.setBackgroundColor(
                        ContextCompat.getColor(requireContext(), R.color.primary_light)
                    )
                    row.addView(createTextField(field.index.toString()))
                    row.addView(createTextField(field.name))
                    row.addView(createTextField(field.side))
                    row.addView(createTextField(field.dose))
                    resultTable.addView(row)
                }
            }
        }
    }

    private fun createTextField(text: String): TextView {
        return TextView(requireContext()).apply {
            val lp = TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
            lp.weight = 1f
            layoutParams = lp
            setTextAppearance(R.style.Theme_Komaric_Text12)
            textAlignment = View.TEXT_ALIGNMENT_CENTER
            setText(text)
        }
    }
}
