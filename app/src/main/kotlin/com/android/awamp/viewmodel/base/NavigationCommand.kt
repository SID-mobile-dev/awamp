package com.android.awamp.viewmodel.base

import androidx.navigation.NavDirections

sealed class NavigationCommand {
    data class ToDest(val directions: NavDirections): NavigationCommand()
    data object Back: NavigationCommand()
}
