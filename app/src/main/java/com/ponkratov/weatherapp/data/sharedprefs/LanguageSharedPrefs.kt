package com.ponkratov.weatherapp.data.sharedprefs

import android.content.SharedPreferences
import androidx.core.content.edit

class LanguageSharedPrefs(
    private val sharedPreferences: SharedPreferences
) {
    fun getLanguageCode(): String {
        return sharedPreferences.getString(LANGUAGE_KEY, "en") ?: "en"
    }

    fun putLanguageCode(languageCode: String) {
        sharedPreferences.edit { putString(LANGUAGE_KEY, languageCode) }
    }

    companion object {
        private const val LANGUAGE_KEY: String = "language"
    }
}