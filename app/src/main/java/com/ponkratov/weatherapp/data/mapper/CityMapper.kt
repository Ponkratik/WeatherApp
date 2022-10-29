package com.ponkratov.weatherapp.data.mapper

import com.ponkratov.weatherapp.data.model.CitiesResponse
import com.ponkratov.weatherapp.data.model.CityEntity
import com.ponkratov.weatherapp.domain.model.City

fun CitiesResponse.toDomainList(): List<City> {
    val citiesList = mutableListOf<City>()
    results.forEach {
        citiesList.add(
            City(
                it.id,
                it.name,
                it.country,
                it.countryCode,
                it.latitude,
                it.longitude,
                it.elevation,
                it.timezone
            )
        )
    }

    return citiesList
}

fun CityEntity.toDomain(): City {
    return City(
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