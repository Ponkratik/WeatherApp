package com.ponkratov.weatherapp.data.api

import com.ponkratov.weatherapp.data.model.CitiesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("forecast&hourly=temperature_2m")
    suspend fun getCities(@Query("latitude") latitude: Double, @Query("longitude") longitude: Double): CitiesResponse
}