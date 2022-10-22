package com.ponkratov.weatherapp.presentation.ui.settings

import androidx.lifecycle.ViewModel
import com.ponkratov.weatherapp.domain.usecase.GetLanguageCodeUseCase
import com.ponkratov.weatherapp.domain.usecase.GetThemeCodeUseCase
import com.ponkratov.weatherapp.domain.usecase.SetLanguageCodeUseCase
import com.ponkratov.weatherapp.domain.usecase.SetThemeCodeUseCase

class SettingsViewModel(
    private val getThemeCodeUseCase: GetThemeCodeUseCase,
    private val setThemeCodeUseCase: SetThemeCodeUseCase,
    private val getLanguageCodeUseCase: GetLanguageCodeUseCase,
    private val setLanguageCodeUseCase: SetLanguageCodeUseCase
): ViewModel() {

    fun onInitGetTheme(): String = getThemeCodeUseCase()
    fun onInitGetLanguage(): String = getLanguageCodeUseCase()

    fun onThemeChecked(themeCode: String) = setThemeCodeUseCase(themeCode)
    fun onLanguageChecked(languageCode: String) = setLanguageCodeUseCase(languageCode)
}