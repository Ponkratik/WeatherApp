package com.ponkratov.weatherapp.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.ponkratov.weatherapp.data.model.WeatherResponse
import com.ponkratov.weatherapp.domain.model.Weather

fun WeatherResponse.toDomainList(): List<Weather> {
    val weatherList = mutableListOf<Weather>()

    requireNotNull(hourly).time.forEach {
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
    val resultMap: Map<String, Double> = hashMapOf()

    for (i in 0..6) {
        val day = times[24 * i]
        val maxTemp = temperatures.subList(24 * i, 24 * (i + 1) - 1).max()
        resultMap.plus(Pair(day, maxTemp))
    }

    return resultMap
}

private fun findMinTempPerDays(
    times: List<String>,
    temperatures: List<Double>
): Map<String, Double> {
    val resultMap: Map<String, Double> = hashMapOf()

    for (i in 0..6) {
        val day = times[24 * i]
        val minTemp = temperatures.subList(24 * i, 24 * (i + 1) - 1).min()
        resultMap.plus(Pair(day, minTemp))
    }

    return resultMap
}

private fun findWeatherCodePerDays(times: List<String>, weatherCodes: List<Int>): Map<String, Int> {
    val resultMap: Map<String, Int> = hashMapOf()

    for (i in 0..6) {
        val day = times[24 * i]
        val tempCode = weatherCodes.subList(24 * i, 24 * (i + 1) - 1)[12]
        resultMap.plus(Pair(day, tempCode))
    }

    return resultMap
}