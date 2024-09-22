package com.aaron.kointest.data.source

import android.content.SharedPreferences

class SettingsDataSource(
    private val pref: SharedPreferences
) {
    fun get(): String {
        return pref.getString("key", "Android") ?: ""
    }

    fun set(value: String): String {
        pref.edit().putString("key", value).apply()
        return value
    }
}