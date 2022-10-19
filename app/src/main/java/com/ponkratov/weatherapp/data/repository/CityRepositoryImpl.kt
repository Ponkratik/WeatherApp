package com.ponkratov.weatherapp.data.repository

import com.ponkratov.weatherapp.data.api.CitiesApi
import com.ponkratov.weatherapp.data.database.CityDao
import com.ponkratov.weatherapp.data.mapper.toData
import com.ponkratov.weatherapp.data.mapper.toDomain
import com.ponkratov.weatherapp.data.mapper.toDomainList
import com.ponkratov.weatherapp.domain.model.City
import com.ponkratov.weatherapp.domain.repository.CityRepository
import org.koin.core.qualifier.QualifierValue

class CityRepositoryImpl(
    private val citiesApi: CitiesApi,
    private val cityDao: CityDao
) : CityRepository {
    override suspend fun getCitiesRemote(query: String): Result<List<City>> = runCatching {
        citiesApi.getCities(query).toDomainList()
    }

    override suspend fun getCitiesLocal(): List<City> {
        return cityDao.getCities().map { it.toDomain() }
    }

    override suspend fun saveCity(city: City) {
        return cityDao.insertCity(city.toData())
    }

    override suspend fun deleteCity(city: City) {
        return cityDao.deleteCity(city.toData())
    }
}