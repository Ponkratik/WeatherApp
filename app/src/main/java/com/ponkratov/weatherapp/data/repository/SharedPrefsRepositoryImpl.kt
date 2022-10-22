package com.ponkratov.weatherapp.data.repository

import com.ponkratov.weatherapp.data.sharedprefs.DarkModeSharedPrefs
import com.ponkratov.weatherapp.data.sharedprefs.LanguageSharedPrefs
import com.ponkratov.weatherapp.domain.repository.SharedPrefsRepository

class SharedPrefsRepositoryImpl(
    private val darkModeSharedPrefs: DarkModeSharedPrefs,
    private val languageSharedPrefs: LanguageSharedPrefs
) : SharedPrefsRepository {
    override fun getThemeStyle(): String = darkModeSharedPrefs.getThemeCode()

    override fun setThemeStyle(themeCode: String) {
        darkModeSharedPrefs.putThemeCode(themeCode)
    }

    override fun getLanguage(): String = languageSharedPrefs.getLanguageCode()

    override fun setLanguage(languageCode: String) {
        languageSharedPrefs.putLanguageCode(languageCode)
    }

}