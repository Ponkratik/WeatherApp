package com.ponkratov.weatherapp.domain.usecase

import com.ponkratov.weatherapp.domain.model.City
import com.ponkratov.weatherapp.domain.repository.CityRepository

class RemoveCityFromFavoritesUseCase(
    private val cityRepository: CityRepository
) {
    suspend operator fun invoke(city: City) = cityRepository.deleteCity(city)
}