package com.ponkratov.weatherapp.presentation.ui.settings

import androidx.lifecycle.ViewModel
import com.ponkratov.weatherapp.domain.model.settings.LanguageCode
import com.ponkratov.weatherapp.domain.model.settings.ThemeCode
import com.ponkratov.weatherapp.domain.usecase.GetLanguageCodeUseCase
import com.ponkratov.weatherapp.domain.usecase.GetThemeCodeUseCase
import com.ponkratov.weatherapp.domain.usecase.SetLanguageCodeUseCase
import com.ponkratov.weatherapp.domain.usecase.SetThemeCodeUseCase

class SettingsViewModel(
    private val getThemeCodeUseCase: GetThemeCodeUseCase,
    private val setThemeCodeUseCase: SetThemeCodeUseCase,
    private val getLanguageCodeUseCase: GetLanguageCodeUseCase,
    private val setLanguageCodeUseCase: SetLanguageCodeUseCase
) : ViewModel() {

    fun onInitGetTheme(): ThemeCode = getThemeCodeUseCase()
    fun onInitGetLanguage(): LanguageCode = getLanguageCodeUseCase()

    fun onThemeChecked(themeCode: ThemeCode) = setThemeCodeUseCase(themeCode)
    fun onLanguageChecked(languageCode: LanguageCode) = setLanguageCodeUseCase(languageCode)
}