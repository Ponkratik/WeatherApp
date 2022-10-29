package com.ponkratov.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("generationtime_ms") var generationtimeMs: Double,
    @SerializedName("hourly_units") var hourlyUnits: HourlyUnits,
    @SerializedName("hourly") var hourly: Hourly
)