package com.ponkratov.weatherapp.domain.usecase

import com.ponkratov.weatherapp.domain.model.Weather
import com.ponkratov.weatherapp.domain.repository.WeatherRepository

class GetWeatherUseCase(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(latitude: Double, longitude: Double): Result<List<Weather>> =
        weatherRepository.getWeatherRemote(latitude, longitude)
}