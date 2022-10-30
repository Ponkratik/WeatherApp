package com.ponkratov.weatherapp.domain.repository

import com.ponkratov.weatherapp.domain.model.settings.LanguageCode
import com.ponkratov.weatherapp.domain.model.settings.ThemeCode

interface SharedPrefsRepository {

    fun getThemeStyle(): ThemeCode

    fun setThemeStyle(themeCode: ThemeCode)

    fun getLanguage(): LanguageCode

    fun setLanguage(languageCode: LanguageCode)
}