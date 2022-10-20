package com.ponkratov.weatherapp.domain.repository

import com.ponkratov.weatherapp.domain.model.Weather

interface WeatherRepository {

    suspend fun getWeatherRemote(latitude: Double, longitude: Double): Result<List<Weather>>
}