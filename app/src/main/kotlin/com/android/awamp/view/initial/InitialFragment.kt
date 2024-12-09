package com.android.awamp.view.initial

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.doOnTextChanged
import com.android.awamp.R
import com.android.awamp.databinding.ScreenInitialBinding
import com.android.awamp.util.hideKeyboard
import com.android.awamp.util.viewBinding
import com.android.awamp.view.base.BaseFragment
import com.android.awamp.viewmodel.initial.InitialViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class InitialFragment : BaseFragment(R.layout.screen_initial) {

    companion object {

        private const val VALID_FIELD_LENGTH = 10
    }

    private val binding by viewBinding(ScreenInitialBinding::bind)

    override val viewModel: InitialViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(
            this.viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.initialTextInput.editText?.isFocused == true) {
                        requireActivity().hideKeyboard(binding.initialTextInput.editText)
                    } else {
                        requireActivity().finish()
                    }
                }
            }
        )

        with(binding) {
            setToolbar(mainToolbar)
            initialTextInput.editText?.doOnTextChanged { text, _, _, _ ->
                if ((text?.length ?: 0) >= VALID_FIELD_LENGTH) {
                    setError()
                } else {
                    initialTextInput.error = null
                    initialNext.isEnabled = (text?.length ?: 0) != 0
                }
            }

            initialTextInput.editText?.setOnKeyListener { view, keyCode, keyEvent ->
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    requireActivity().hideKeyboard(initialTextInput.editText)
                    true
                } else {
                    false
                }
            }

            root.setOnClickListener {
                requireActivity().hideKeyboard(initialTextInput.editText)
            }

            initialNext.setOnClickListener {
                val weight = initialTextInput.editText?.text?.toString()?.toFloatOrNull()
                if (weight != null) {
                    viewModel.onNextClicked(weight)
                } else {
                    setError()
                }
            }
        }
    }

    override fun setToolbar(toolbar: Toolbar) {
        toolbar.setNavigationOnClickListener {
            requireActivity().finish()
        }
    }

    private fun setError() {
        binding.initialTextInput.error = getString(R.string.invalid_data_text)
        binding.initialNext.isEnabled = false
    }
}
