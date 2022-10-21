package com.ponkratov.weatherapp.domain.usecase

import com.ponkratov.weatherapp.domain.repository.CityRepository

class GetFavoritesCitiesUseCase(
    private val cityRepository: CityRepository
) {
    suspend operator fun invoke() = cityRepository.getCitiesLocal()
}