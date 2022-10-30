package com.ponkratov.weatherapp.domain.usecase

import com.ponkratov.weatherapp.domain.model.settings.ThemeCode
import com.ponkratov.weatherapp.domain.repository.SharedPrefsRepository

class SetThemeCodeUseCase(
    private val sharedPrefsRepository: SharedPrefsRepository
) {
    operator fun invoke(themeCode: ThemeCode) = sharedPrefsRepository.setThemeStyle(themeCode)
}