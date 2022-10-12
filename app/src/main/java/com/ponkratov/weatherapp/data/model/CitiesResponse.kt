package com.ponkratov.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class CitiesResponse(
    @SerializedName("results") var results: List<CityDTO> = listOf(),
    @SerializedName("generationtime_ms") var generationtimeMs: Double? = null
)