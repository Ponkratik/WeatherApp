package com.ponkratov.weatherapp.domain.usecase

import com.ponkratov.weatherapp.domain.model.settings.LanguageCode
import com.ponkratov.weatherapp.domain.repository.SharedPrefsRepository

class GetLanguageCodeUseCase(
    private val sharedPrefsRepository: SharedPrefsRepository
) {

    operator fun invoke(): LanguageCode = sharedPrefsRepository.getLanguage()
}