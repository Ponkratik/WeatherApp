package com.ponkratov.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("generationtime_ms") var generationtimeMs: Double? = null,
    @SerializedName("hourly_units") var hourlyUnits: HourlyUnits? = HourlyUnits(),
    @SerializedName("hourly") var hourly: Hourly? = Hourly()
)