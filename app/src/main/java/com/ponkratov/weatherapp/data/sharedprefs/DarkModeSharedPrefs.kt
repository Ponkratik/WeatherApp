package com.ponkratov.weatherapp.data.sharedprefs

import android.content.SharedPreferences
import androidx.core.content.edit

class DarkModeSharedPrefs(
    private val sharedPreferences: SharedPreferences
) {
    fun getThemeCode(): String {
        return sharedPreferences.getString(THEME_KEY, "system") ?: "system"
    }

    fun putThemeCode(themeCode: String) {
        sharedPreferences.edit { putString(THEME_KEY, themeCode) }
    }

    companion object {
        private const val THEME_KEY: String = "theme"
    }
}