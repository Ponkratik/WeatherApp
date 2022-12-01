package com.ponkratov.weatherapp.domain.model

data class WeatherUI(
    val weatherList: List<Weather>,
    val currentTemperature: String
)