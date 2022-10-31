package com.ponkratov.weatherapp.domain.model

data class Weather(
    var date: String,
    var minTemp: String,
    var maxTemp: String,
    var weatherCode: Int
)