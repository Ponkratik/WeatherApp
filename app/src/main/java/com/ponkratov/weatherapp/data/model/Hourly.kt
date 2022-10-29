package com.ponkratov.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class Hourly(

    @SerializedName("time") val time: List<String>,
    @SerializedName("temperature_2m") val temperature2m: List<Double>,
    @SerializedName("weathercode") val weatherCode: List<Int>
)
