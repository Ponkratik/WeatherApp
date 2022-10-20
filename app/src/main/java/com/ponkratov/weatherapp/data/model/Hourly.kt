package com.ponkratov.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class Hourly(

    @SerializedName("time") var time: List<String> = listOf(),
    @SerializedName("temperature_2m") var temperature2m: List<Double> = listOf(),
    @SerializedName("weathercode") var weatherCode: List<Int> = listOf()
)
