package com.ponkratov.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class HourlyUnits(

    @SerializedName("time") var time: String,
    @SerializedName("temperature_2m") var temperature2m: String,
    @SerializedName("weathercode") var weatherCode: String
)