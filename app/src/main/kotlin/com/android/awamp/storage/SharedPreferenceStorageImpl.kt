package com.android.awamp.storage

import android.content.Context
import androidx.core.content.edit
import com.android.awamp.util.exception.UnsupportedTypeException

@Suppress("UNCHECKED_CAST")
class SharedPreferenceStorageImpl(context: Context): SharedPreferenceStorage {

    private val sharedPreferences = context.getSharedPreferences("name", Context.MODE_PRIVATE)

    override fun <T> putValue(key: String, value: T) {
        sharedPreferences.edit {
            when (value) {
                is String -> {
                    putString(key, value)
                }

                is Boolean -> {
                    putBoolean(key, value)
                }

                is Long -> {
                    putLong(key, value)
                }

                is Int -> {
                    putInt(key, value)
                }

                is Double -> {
                    putFloat(key, value.toFloat())
                }

                is Float -> {
                    putFloat(key, value)
                }

                else -> throw UnsupportedTypeException()
            }
        }
    }

    override fun <T> getValue(key: String, defValue: T): T {
        return with(sharedPreferences) {
            when (defValue) {
                is String -> {
                    getString(key, defValue) as T
                }

                is Boolean -> {
                    getBoolean(key, defValue) as T
                }

                is Long -> {
                    getLong(key, defValue) as T
                }

                is Int -> {
                    getInt(key, defValue) as T
                }

                is Double -> {
                    getFloat(key, defValue.toFloat()) as T
                }

                is Float -> {
                    getFloat(key, defValue) as T
                }

                else -> throw UnsupportedTypeException()
            }
        }
    }
}
