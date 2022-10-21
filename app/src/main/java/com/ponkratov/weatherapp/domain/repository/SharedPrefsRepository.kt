package com.ponkratov.weatherapp.domain.repository

interface SharedPrefsRepository {

    fun getThemeStyle(): String

    fun setThemeStyle(themeCode: String)

    fun getLanguage(): String

    fun setLanguage(languageCode: String)
}