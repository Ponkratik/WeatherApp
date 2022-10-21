package com.ponkratov.weatherapp.presentation.ui.settings

import androidx.lifecycle.ViewModel
import com.ponkratov.weatherapp.domain.usecase.GetThemeCodeUseCase
import com.ponkratov.weatherapp.domain.usecase.SetThemeCodeUseCase

class SettingsViewModel(
    private val getThemeCodeUseCase: GetThemeCodeUseCase,
    private val setThemeCodeUseCase: SetThemeCodeUseCase
): ViewModel() {

    fun onInit(): String = getThemeCodeUseCase()

    fun onDayThemeChecked(themeCode: String) = setThemeCodeUseCase(themeCode)
    fun onNightThemeChecked(themeCode: String) = setThemeCodeUseCase(themeCode)
    fun onSystemThemeChecked(themeCode: String) = setThemeCodeUseCase(themeCode)
}