package com.ponkratov.weatherapp.data.service

import android.content.Context
import com.ponkratov.weatherapp.domain.model.settings.LanguageCode
import com.ponkratov.weatherapp.domain.model.settings.ThemeCode
import com.ponkratov.weatherapp.domain.service.LanguageService
import com.ponkratov.weatherapp.domain.service.ThemeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PreferencesService(context: Context) : ThemeService, LanguageService {

    private val sharedPrefs =
        context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)

    override var themeCode: ThemeCode by PreferencesDelegate(
        sharedPrefs,
        getValue = {
            when (getString(THEME_KEY, null)) {
                ThemeCode.THEME_CODE_NIGHT.themeCode -> ThemeCode.THEME_CODE_NIGHT
                ThemeCode.THEME_CODE_DAY.themeCode -> ThemeCode.THEME_CODE_DAY
                ThemeCode.THEME_CODE_SYSTEM.themeCode -> ThemeCode.THEME_CODE_SYSTEM
                else -> ThemeCode.THEME_CODE_DEFAULT
            }
        },
        setValue = {
            putString(LANGUAGE_KEY, it.themeCode)
        }
    )

    override var language: LanguageCode by PreferencesDelegate(
        sharedPrefs,
        getValue = {
            when (getString(LANGUAGE_KEY, null)) {
                LANGUAGE_RU_CODE -> LanguageCode.LANGUAGE_CODE_RU
                LANGUAGE_EN_CODE -> LanguageCode.LANGUAGE_CODE_EN
                else -> LanguageCode.LANGUAGE_CODE_DEFAULT
            }
        },
        setValue = {
            val languageCodeText = when (it) {
                LanguageCode.LANGUAGE_CODE_EN -> LANGUAGE_EN_CODE
                LanguageCode.LANGUAGE_CODE_RU -> LANGUAGE_RU_CODE
            }
            putString(LANGUAGE_KEY, languageCodeText)
            _languageFlow.tryEmit(it)
        }
    )

    private val _languageFlow = MutableStateFlow(language)
    override val languageFlow: Flow<LanguageCode> = _languageFlow.asStateFlow()

    companion object {
        private const val SHARED_PREFERENCE_NAME = "weather_app_shared_prefs"

        private const val THEME_KEY: String = "theme"
        private const val LANGUAGE_KEY: String = "language"

        private const val LANGUAGE_EN_CODE = "en"
        private const val LANGUAGE_RU_CODE = "ru"
    }
}