package com.ponkratov.weatherapp.data.mapper

import com.ponkratov.weatherapp.data.model.WeatherResponse
import com.ponkratov.weatherapp.domain.model.Weather

fun WeatherResponse.toDomain(): Weather {
    val dataMap: Map<String, String> = emptyMap()

    for (i in (0..requireNotNull(hourly).time.size)) {
        dataMap.plus(
            Pair(
                requireNotNull(hourly).time[i],
                "${requireNotNull(hourly).temperature2m[i]} ${requireNotNull(hourlyUnits).temperature2m ?: ""}"
            )
        )
    }

    return Weather(dataMap)
}