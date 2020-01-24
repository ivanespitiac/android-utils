package com.ivanespitia.androidutils.utils

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Suppress("UNUSED")
class Delegate<T>(
    private val preferences: SharedPreferences,
    private val default: T
): ReadWriteProperty<Any?, T> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return getData(property.name, default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        setData(property.name, value)
    }

    private fun <T> getData(key: String, default: T): T = with(preferences) {
        val result = when (default) {
            is String -> getString(key, default) ?: default
            is Int -> getInt(key, default)
            is Long -> getLong(key, default)
            is Float -> getFloat(key, default)
            is Boolean -> getBoolean(key, default)
            is Set<*> -> getStringSet(key, mutableSetOf<String>()) ?: mutableSetOf<String>()
            else -> throw IllegalArgumentException("This type cannot be saved into Preferences")
        }
        @Suppress("UNCHECKED_CAST")
        return result as T
    }

    private fun <T> setData(key: String, value: T) = with(preferences.edit()) {
        when (value) {
            is String -> putString(key, value).apply()
            is Int -> putInt(key, value).apply()
            is Long -> putLong(key, value).apply()
            is Float -> putFloat(key, value).apply()
            is Boolean -> putBoolean(key, value).apply()
            is Set<*> -> {
                val set = mutableSetOf<String>()
                value.forEach {
                    it?.let { safe ->
                        if (safe is String) {
                            set.add(safe)
                        }
                    }
                }
                preferences.edit().putStringSet(key, set).apply()
            }
            else -> throw IllegalArgumentException("This type cannot be saved into Preferences")
        }
    }

}