package com.ponkratov.weatherapp.domain.usecase

import com.ponkratov.weatherapp.domain.repository.SharedPrefsRepository

class SetLanguageCodeUseCase(
    private val sharedPrefsRepository: SharedPrefsRepository
) {

    operator fun invoke(languageCode: String) {
        sharedPrefsRepository.setLanguage(languageCode)
    }
}