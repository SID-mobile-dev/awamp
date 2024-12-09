package com.android.awamp.viewmodel.chooseSide

import com.android.awamp.domain.Side
import com.android.awamp.view.sideChoose.ChooseSideFragmentDirections
import com.android.awamp.viewmodel.base.BaseViewModel
import com.android.awamp.viewmodel.base.NavigationCommand

class ChooseSideViewModel : BaseViewModel() {

    fun saveSide(name: String, side: Side) {
        navigate(
            NavigationCommand.ToDest(
                ChooseSideFragmentDirections.actionChooseSideFragmentToInputFragment(name, side)
            )
        )
    }
}
