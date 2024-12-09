package com.android.awamp.view.input

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.navArgs
import com.android.awamp.R
import com.android.awamp.databinding.ScreenInputBinding
import com.android.awamp.util.hideKeyboard
import com.android.awamp.util.observe
import com.android.awamp.util.viewBinding
import com.android.awamp.view.base.BaseFragment
import com.android.awamp.viewmodel.input.InputViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class InputFragment : BaseFragment(R.layout.screen_input) {

    override val viewModel: InputViewModel by viewModel { parametersOf(args) }

    private val args: InputFragmentArgs by navArgs()

    private val binding by viewBinding(ScreenInputBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        with(binding) {
            setToolbar(inputToolbar)
            inputField.editText?.doOnTextChanged { text, _, _, _ ->
                viewModel.validateValue(text?.toString())
            }
            root.setOnClickListener {
                requireActivity().hideKeyboard(inputField.editText)
            }
            inputSave.setOnClickListener {
                viewModel.setDose(inputField.editText?.text?.toString())
            }
        }
    }

    private fun observeData() {
        observe(viewModel.state) { state ->
            binding.inputToolbar.title = state.toolbarTitle
            binding.inputSide.editText?.setText(state.sideText)
            if (state.dose != null && state.dose != 0.0f) {
                binding.inputFieldEditText.setText(state.dose.toString())
            }
            binding.inputMaxDoseEditText.setText("%.2f".format(state.maxDose))
            binding.inputIntervalEditText.setText(state.interval)
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) { error ->
            with(binding) {
                inputField.error = if (error.hasError) {
                    error.errorText
                } else {
                    null
                }
                inputSave.isEnabled = inputFieldEditText.text?.length != null &&
                    inputFieldEditText.text?.length != 0 && !error.hasError
            }
        }
    }
}
