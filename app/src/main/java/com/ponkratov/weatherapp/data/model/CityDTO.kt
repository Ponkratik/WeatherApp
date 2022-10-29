package com.ponkratov.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class CityDTO(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("country_code") val countryCode: String,
    @SerializedName("country") val country: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("elevation") val elevation: Long,
    @SerializedName("timezone") val timezone: String
)