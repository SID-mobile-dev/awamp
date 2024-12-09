package com.android.awamp.view.base

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.awamp.viewmodel.base.BaseViewModel
import com.android.awamp.viewmodel.base.NavigationCommand

abstract class BaseFragment(layoutId: Int = 0) : Fragment(layoutId) {

    abstract val viewModel: BaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(
            this.viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            }
        )

        observe()
    }

    open fun setToolbar(toolbar: Toolbar) {
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observe() {
        viewModel.navigation.observe(viewLifecycleOwner) { event ->
            event.getContentEventful()?.let(::handleCommand)
        }
    }

    private fun handleCommand(command: NavigationCommand) {
        when (command) {
            is NavigationCommand.ToDest -> {
                findNavController().navigate(command.directions)
            }

            is NavigationCommand.Back -> {
                findNavController().popBackStack()
            }
        }
    }
}
