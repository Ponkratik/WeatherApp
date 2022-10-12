package com.ponkratov.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class CityDTO(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("country_code") var countryCode: String? = null,
    @SerializedName("country") var country: String? = null,
    @SerializedName("latitude") var latitude: Double? = null,
    @SerializedName("longitude") var longitude: Double? = null,
    @SerializedName("elevation") var elevation: Int? = null,
    @SerializedName("timezone") var timezone: String? = null
)