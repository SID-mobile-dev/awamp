package com.android.awamp.viewmodel.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.awamp.viewmodel.util.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class BaseViewModel : ViewModel() {

    private val _navigation = MutableLiveData<Event<NavigationCommand>>()
    val navigation: LiveData<Event<NavigationCommand>> = _navigation

    fun navigate(directions: NavigationCommand) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                _navigation.value = Event(directions)
            }
        }
    }

    fun navigateBack() {
        _navigation.value = Event(NavigationCommand.Back)
    }
}
