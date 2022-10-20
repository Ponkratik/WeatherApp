package com.ponkratov.weatherapp.data.mapper

import com.ponkratov.weatherapp.data.model.WeatherResponse
import com.ponkratov.weatherapp.domain.model.Weather

fun WeatherResponse.toDomainList(): List<Weather> {
    val weatherList = mutableListOf<Weather>()

    requireNotNull(hourly).time = requireNotNull(hourly).time.map {
        it.split("T")[0]
    }

    val maxTempMap =
        findMaxTempPerDays(requireNotNull(hourly).time, requireNotNull(hourly).temperature2m)
    val minTempMap =
        findMinTempPerDays(requireNotNull(hourly).time, requireNotNull(hourly).temperature2m)
    val weatherCodeMap =
        findWeatherCodePerDays(requireNotNull(hourly).time, requireNotNull(hourly).weatherCode)

    maxTempMap.forEach { (date, maxTemp) ->
        weatherList.add(
            Weather(
                date,
                "${minTempMap[date]} ${requireNotNull(hourlyUnits).temperature2m ?: ""}",
                "$maxTemp ${requireNotNull(hourlyUnits).temperature2m ?: ""}",
                weatherCodeMap[date] ?: 100
            )
        )
    }

    return weatherList
}

private fun findMaxTempPerDays(
    times: List<String>,
    temperatures: List<Double>
): Map<String, Double> {
    val resultMap: MutableMap<String, Double> = mutableMapOf()

    for (i in 0..6) {
        val day = times[24 * i]
        val maxTemp = temperatures.subList(24 * i, 24 * (i + 1) - 1).max()
        resultMap[day] = maxTemp
    }

    return resultMap
}

private fun findMinTempPerDays(
    times: List<String>,
    temperatures: List<Double>
): Map<String, Double> {
    val resultMap: MutableMap<String, Double> = mutableMapOf()

    for (i in 0..6) {
        val day = times[24 * i]
        val minTemp = temperatures.subList(24 * i, 24 * (i + 1) - 1).min()
        resultMap[day] = minTemp
    }

    return resultMap
}

private fun findWeatherCodePerDays(times: List<String>, weatherCodes: List<Int>): Map<String, Int> {
    val resultMap: MutableMap<String, Int> = mutableMapOf()

    for (i in 0..6) {
        val day = times[24 * i]
        val tempCode = weatherCodes.subList(24 * i, 24 * (i + 1) - 1)[12]
        resultMap[day] = tempCode
    }

    return resultMap
}