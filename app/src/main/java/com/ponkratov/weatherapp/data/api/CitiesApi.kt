package com.ponkratov.weatherapp.data.api

import com.ponkratov.weatherapp.data.model.CitiesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CitiesApi {

    @GET("search")
    suspend fun getCities(@Query("name") name: String): CitiesResponse
}