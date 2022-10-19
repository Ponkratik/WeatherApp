package com.ponkratov.weatherapp.data.mapper

import com.ponkratov.weatherapp.data.model.CitiesResponse
import com.ponkratov.weatherapp.data.model.CityEntity
import com.ponkratov.weatherapp.domain.model.City

fun CitiesResponse.toDomainList(): List<City> {
    val citiesList = mutableListOf<City>()
    results.forEach {
        citiesList.add(
            City(
                it.id ?: 0,
                it.name ?: "",
                it.country ?: "",
                it.countryCode ?: "",
                it.latitude ?: 0.0,
                it.longitude ?: 0.0,
                it.elevation ?: 0,
                it.timezone ?: ""
            )
        )
    }

    return citiesList
}

fun CityEntity.toDomain(): City {
    return City(
        id ?: 0,
        name ?: "",
        country ?: "",
        countryCode ?: "",
        latitude ?: 0.0,
        longitude ?: 0.0,
        elevation ?: 0,
        timezone ?: ""
    )
}

fun City.toData(): CityEntity {
    return CityEntity(
        id,
        name,
        country,
        countryCode,
        latitude,
        longitude,
        elevation,
        timezone
    )
}