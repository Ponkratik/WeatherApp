package com.ponkratov.weatherapp.domain.model

import java.time.DayOfWeek

data class Weather(
    var date: String,
    var minTemp: String,
    var maxTemp: String,
    var weatherCode: Int
)