package com.ponkratov.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class Hourly(

    @SerializedName("time") var time: List<String>,
    @SerializedName("temperature_2m") var temperature2m: List<Double>,
    @SerializedName("weathercode") var weatherCode: List<Int>
)
