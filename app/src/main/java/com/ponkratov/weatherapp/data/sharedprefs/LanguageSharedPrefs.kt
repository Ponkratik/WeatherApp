package com.ponkratov.weatherapp.data.sharedprefs

import android.content.SharedPreferences
import androidx.core.content.edit
import com.ponkratov.weatherapp.domain.model.settings.LanguageCode

class LanguageSharedPrefs(
    private val sharedPreferences: SharedPreferences
) {
    fun getLanguageCode(): LanguageCode {
        return when (sharedPreferences.getString(LANGUAGE_KEY, "en")) {
            LANGUAGE_RU_CODE -> LanguageCode.LANGUAGE_CODE_RU
            LANGUAGE_EN_CODE -> LanguageCode.LANGUAGE_CODE_EN
            else -> LanguageCode.LANGUAGE_CODE_DEFAULT
        }
    }

    fun putLanguageCode(languageCode: LanguageCode) {
        sharedPreferences.edit {
            putString(
                LANGUAGE_KEY,
                when (languageCode) {
                    LanguageCode.LANGUAGE_CODE_EN -> LANGUAGE_EN_CODE
                    LanguageCode.LANGUAGE_CODE_RU -> LANGUAGE_RU_CODE
                }
            )
        }
    }

    companion object {
        private const val LANGUAGE_KEY: String = "language"

        private const val LANGUAGE_EN_CODE = "en"
        private const val LANGUAGE_RU_CODE = "ru"
    }
}