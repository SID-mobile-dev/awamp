package com.android.awamp.viewmodel.util

class Event<out T>(private val content: T) {

    private var isHandled: Boolean = false

    fun getContentEventful(): T? {
        if (isHandled) return null
        isHandled = true
        return content
    }

    fun getContentCurrently(): T? {
        return content
    }
}
