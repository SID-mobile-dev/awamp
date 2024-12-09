package com.android.awamp.viewmodel.initial

import com.android.awamp.storage.SharedPreferenceStorage
import com.android.awamp.storage.SpKeys
import com.android.awamp.view.initial.InitialFragmentDirections
import com.android.awamp.viewmodel.base.BaseViewModel
import com.android.awamp.viewmodel.base.NavigationCommand

class InitialViewModel(
    private val preferenceStorage: SharedPreferenceStorage,
) : BaseViewModel() {

    init {
        preferenceStorage.putValue(SpKeys.WEIGHT_KEY, 0f)
        preferenceStorage.putValue(SpKeys.CURRENT_DOSE, 0f)
    }

    fun onNextClicked(weight: Float) {
        saveData(weight)
        navigateToMainScreen()
    }

    private fun saveData(weight: Float) {
        preferenceStorage.putValue(SpKeys.WEIGHT_KEY, weight)
    }

    private fun navigateToMainScreen() {
        navigate(
            NavigationCommand.ToDest(
                InitialFragmentDirections.actionInitialFragmentToMainFragment()
            )
        )
    }
}
