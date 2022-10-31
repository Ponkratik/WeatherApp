package com.ponkratov.weatherapp.data.repository

import com.ponkratov.weatherapp.data.api.WeatherApi
import com.ponkratov.weatherapp.data.mapper.toDomainList
import com.ponkratov.weatherapp.domain.model.WeatherUI
import com.ponkratov.weatherapp.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi
) : WeatherRepository {
    override suspend fun getWeatherRemote(
        latitude: Double,
        longitude: Double
    ): Result<WeatherUI> = runCatching {
        weatherApi.getWeather(latitude, longitude).toDomainList()
    }
}