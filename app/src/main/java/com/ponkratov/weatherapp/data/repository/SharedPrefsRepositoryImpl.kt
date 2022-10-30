package com.ponkratov.weatherapp.data.repository

import com.ponkratov.weatherapp.data.sharedprefs.DarkModeSharedPrefs
import com.ponkratov.weatherapp.data.sharedprefs.LanguageSharedPrefs
import com.ponkratov.weatherapp.domain.model.settings.LanguageCode
import com.ponkratov.weatherapp.domain.model.settings.ThemeCode
import com.ponkratov.weatherapp.domain.repository.SharedPrefsRepository

class SharedPrefsRepositoryImpl(
    private val darkModeSharedPrefs: DarkModeSharedPrefs,
    private val languageSharedPrefs: LanguageSharedPrefs
) : SharedPrefsRepository {
    override fun getThemeStyle(): ThemeCode = darkModeSharedPrefs.getThemeCode()

    override fun setThemeStyle(themeCode: ThemeCode) {
        darkModeSharedPrefs.putThemeCode(themeCode)
    }

    override fun getLanguage(): LanguageCode = languageSharedPrefs.getLanguageCode()

    override fun setLanguage(languageCode: LanguageCode) {
        languageSharedPrefs.putLanguageCode(languageCode)
    }

}