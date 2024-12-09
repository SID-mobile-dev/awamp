package com.android.awamp.view.main

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.awamp.R
import com.android.awamp.databinding.ScreenMainBinding
import com.android.awamp.util.observe
import com.android.awamp.util.viewBinding
import com.android.awamp.view.base.BaseFragment
import com.android.awamp.view.util.SimpleItemDecoration
import com.android.awamp.viewmodel.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment(R.layout.screen_main) {

    private val binding by viewBinding(ScreenMainBinding::bind)

    override val viewModel: MainViewModel by viewModel()

    private val mainAdapter: MainAdapter = MainAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.mainList) {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(SimpleItemDecoration())
        }
        setToolbar(binding.mainToolbar)
        observeData()

        binding.mainGetResult.setOnClickListener {
            viewModel.onResultCLicked()
        }
    }

    private fun observeData() {
        observe(viewModel.state) { uiState ->
            mainAdapter.setData(uiState.rows)
            binding.mainGetResult.isEnabled = uiState.isActionEnabled
        }
    }
}
