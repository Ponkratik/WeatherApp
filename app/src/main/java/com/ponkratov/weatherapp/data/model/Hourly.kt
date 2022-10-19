package com.ponkratov.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class Hourly(

    @SerializedName("time") val time: List<String> = listOf(),
    @SerializedName("temperature_2m") val temperature2m: List<Double> = listOf(),
    @SerializedName("weathercode") val weatherCode: List<Int> = listOf()
)
