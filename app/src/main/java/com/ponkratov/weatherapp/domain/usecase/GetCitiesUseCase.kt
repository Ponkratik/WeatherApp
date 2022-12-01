package com.ponkratov.weatherapp.domain.usecase

import com.ponkratov.weatherapp.domain.model.City
import com.ponkratov.weatherapp.domain.repository.CityRepository

class GetCitiesUseCase(
    private val cityRepository: CityRepository
) {
    suspend operator fun invoke(query: String): Result<List<City>> =
        cityRepository.getCitiesRemote(query)
}