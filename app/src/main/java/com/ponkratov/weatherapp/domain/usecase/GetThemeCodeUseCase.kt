package com.ponkratov.weatherapp.domain.usecase

import com.ponkratov.weatherapp.domain.repository.SharedPrefsRepository

class GetThemeCodeUseCase(
    private val sharedPrefsRepository: SharedPrefsRepository
) {
    operator fun invoke(): String = sharedPrefsRepository.getThemeStyle()
}