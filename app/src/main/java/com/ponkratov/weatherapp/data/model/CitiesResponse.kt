package com.ponkratov.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class CitiesResponse(
    @SerializedName("results") val results: List<CityDTO>,
    @SerializedName("generationtime_ms") val generationtimeMs: Double
)