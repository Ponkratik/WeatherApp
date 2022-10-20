package com.ponkratov.weatherapp.data.api

import com.ponkratov.weatherapp.data.model.CitiesResponse
import com.ponkratov.weatherapp.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("forecast?hourly=temperature_2m,weathercode")
    suspend fun getWeather(@Query("latitude") latitude: Double, @Query("longitude") longitude: Double): WeatherResponse
}