package com.ponkratov.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class CityDTO(
    @SerializedName("id") var id: Long,
    @SerializedName("name") var name: String,
    @SerializedName("country_code") var countryCode: String,
    @SerializedName("country") var country: String,
    @SerializedName("latitude") var latitude: Double,
    @SerializedName("longitude") var longitude: Double,
    @SerializedName("elevation") var elevation: Long,
    @SerializedName("timezone") var timezone: String
)