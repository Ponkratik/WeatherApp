package com.ponkratov.weatherapp.data.sharedprefs

import android.content.SharedPreferences
import androidx.core.content.edit
import com.ponkratov.weatherapp.domain.model.settings.ThemeCode

class DarkModeSharedPrefs(
    private val sharedPreferences: SharedPreferences
) {
    fun getThemeCode(): ThemeCode {
        return when (sharedPreferences.getString(
            THEME_KEY,
            ThemeCode.THEME_CODE_DEFAULT.themeCode
        ) ?: ThemeCode.THEME_CODE_DEFAULT.themeCode) {
            ThemeCode.THEME_CODE_DAY.themeCode -> ThemeCode.THEME_CODE_DAY
            ThemeCode.THEME_CODE_NIGHT.themeCode -> ThemeCode.THEME_CODE_NIGHT
            else -> ThemeCode.THEME_CODE_SYSTEM
        }
    }

    fun putThemeCode(themeCode: ThemeCode) {
        sharedPreferences.edit { putString(THEME_KEY, themeCode.themeCode) }
    }

    companion object {
        private const val THEME_KEY: String = "theme"
    }
}