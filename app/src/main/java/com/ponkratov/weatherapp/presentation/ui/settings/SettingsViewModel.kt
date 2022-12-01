package com.ponkratov.weatherapp.presentation.ui.settings

import androidx.lifecycle.ViewModel
import com.ponkratov.weatherapp.domain.model.settings.LanguageCode
import com.ponkratov.weatherapp.domain.model.settings.ThemeCode
import com.ponkratov.weatherapp.domain.service.LanguageService
import com.ponkratov.weatherapp.domain.service.ThemeService

class SettingsViewModel(
    private val languageService: LanguageService,
    private val themeService: ThemeService
) : ViewModel() {

    var currentLanguageCode: LanguageCode by languageService::language
    var currentThemeCode: ThemeCode by themeService::themeCode
}