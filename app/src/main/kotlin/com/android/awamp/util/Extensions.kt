package com.android.awamp.util

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.android.awamp.viewmodel.util.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

fun Activity.hideKeyboard(view: View?) {
    if (view == null) {
        return
    }
    view.clearFocus()
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

infix fun ViewGroup.inflate(layoutId: Int): View {
    return LayoutInflater.from(this.context).inflate(layoutId, this, false)
}

inline fun <T : Any> LifecycleOwner.observe(event: Flow<T>, crossinline block: (data: T) -> Unit) {
    lifecycleScope.launch {
        event.flowWithLifecycle(
            this@observe.lifecycle,
            Lifecycle.State.STARTED
        ).collect { state ->
            block(state)
        }
    }
}

inline fun <T : Any> LifecycleOwner.observeEvent(
    event: Flow<Event<T>>,
    crossinline block: (data: T) -> Unit
) {
    lifecycleScope.launch {
        event.flowWithLifecycle(
            this@observeEvent.lifecycle,
            Lifecycle.State.STARTED
        ).collectLatest { event ->
            event.getContentEventful()?.let(block)
        }
    }
}

fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).roundToInt()
