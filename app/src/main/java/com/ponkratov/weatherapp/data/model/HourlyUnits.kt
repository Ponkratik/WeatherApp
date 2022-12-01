package com.ponkratov.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class HourlyUnits(

    @SerializedName("time") val time: String,
    @SerializedName("temperature_2m") val temperature2m: String,
    @SerializedName("weathercode") val weatherCode: String
)