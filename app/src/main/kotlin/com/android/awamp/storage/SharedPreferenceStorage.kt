package com.android.awamp.storage

interface SharedPreferenceStorage {

    fun <T> putValue(key: String, value: T)
    fun <T> getValue(key: String, defValue: T): T
}
