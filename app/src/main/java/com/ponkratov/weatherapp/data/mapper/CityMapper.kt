package com.ponkratov.weatherapp.data.mapper

import com.ponkratov.weatherapp.data.model.CitiesResponse
import com.ponkratov.weatherapp.data.model.CityEntity
import com.ponkratov.weatherapp.domain.model.City

fun CitiesResponse.toDomain(): City {
    val firstResult = results.first()
    return City(
        firstResult.id ?: 0 ,
        firstResult.name ?: "",
        firstResult.country ?: "",
        firstResult.countryCode ?: "",
        firstResult.latitude ?: 0.0,
        firstResult.longitude ?: 0.0,
        firstResult.elevation ?: 0,
        firstResult.timezone ?: ""
    )
}

fun CityEntity.toDomain(): City {
    return City(
        id ?: 0 ,
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