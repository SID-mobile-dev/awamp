package com.android.awamp.view.sideChoose

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.android.awamp.R
import com.android.awamp.databinding.ScreenChooseSideBinding
import com.android.awamp.domain.Side
import com.android.awamp.util.viewBinding
import com.android.awamp.view.base.BaseFragment
import com.android.awamp.viewmodel.chooseSide.ChooseSideViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChooseSideFragment : BaseFragment(R.layout.screen_choose_side) {

    override val viewModel: ChooseSideViewModel by viewModel()

    private val args: ChooseSideFragmentArgs by navArgs()

    private val binding by viewBinding(ScreenChooseSideBinding::bind)

    private var side: Side = Side.LEFT

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbar(binding.chooseSideToolbar)
        binding.chooseSideRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.chooseSideRadioLeft.id -> {
                    side = Side.LEFT
                }

                binding.chooseSideRadioRight.id -> {
                    side = Side.RIGHT
                }
            }

        }
        binding.chooseSideNext.setOnClickListener {
            viewModel.saveSide(args.name, side)
        }
    }
}
