package com.ponkratov.weatherapp.domain.repository

import com.ponkratov.weatherapp.domain.model.City

interface CityRepository {

    suspend fun getCitiesRemote(query: String): Result<List<City>>

    suspend fun getCitiesLocal(): List<City>

    suspend fun saveCity(city: City)

    suspend fun deleteCity(city: City)
}