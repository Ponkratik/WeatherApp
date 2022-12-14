package com.ponkratov.weatherapp.data.mapper

import com.ponkratov.weatherapp.data.model.WeatherResponse
import com.ponkratov.weatherapp.domain.model.Weather
import com.ponkratov.weatherapp.domain.model.WeatherUI
import java.text.SimpleDateFormat
import java.util.*

fun WeatherResponse.toDomainList(): WeatherUI {

    val maxTempMap =
        findTempPerDays(hourly.time, hourly.temperature2m, true)
    val minTempMap =
        findTempPerDays(hourly.time, hourly.temperature2m, false)
    val weatherCodeMap =
        findWeatherCodePerDays(hourly.time, hourly.weatherCode)

    val sdf = SimpleDateFormat("yyyy-MM-dd hh:", Locale.US)
    val currentDate = sdf.format(Date()).replace(' ', 'T') + "00"
    val currentTemp = hourly.temperature2m[hourly.time.indexOf(currentDate)]

    return WeatherUI(
        maxTempMap.map { (date, maxTemp) ->
            Weather(
                date,
                "${minTempMap[date]} ${hourlyUnits.temperature2m}",
                "$maxTemp ${hourlyUnits.temperature2m}",
                weatherCodeMap[date] ?: DEFAULT_WEATHER_CODE
            )
        },
        "$currentTemp ${hourlyUnits.temperature2m}"
    )
}

private fun findTempPerDays(
    times: List<String>,
    temperatures: List<Double>,
    findMax: Boolean
): Map<String, Double> {
    val resultMap: MutableMap<String, Double> = mutableMapOf()

    for (i in 0 until DAYS_IN_WEEK) {
        val day = times[HOURS_PER_DAY * i].split("T").first()
        resultMap[day] = if (findMax) {
            temperatures.subList(HOURS_PER_DAY * i, HOURS_PER_DAY * (i + 1) - 1).max()
        } else {
            temperatures.subList(HOURS_PER_DAY * i, HOURS_PER_DAY * (i + 1) - 1).min()
        }
    }

    return resultMap
}

private fun findWeatherCodePerDays(times: List<String>, weatherCodes: List<Int>): Map<String, Int> {
    val resultMap: MutableMap<String, Int> = mutableMapOf()

    for (i in 0 until DAYS_IN_WEEK) {
        val day = times[HOURS_PER_DAY * i].split("T").first()
        val tempCode = weatherCodes.subList(HOURS_PER_DAY * i, HOURS_PER_DAY * (i + 1) - 1)[HOURS_PER_DAY / 2]
        resultMap[day] = tempCode
    }

    return resultMap
}

private const val HOURS_PER_DAY = 24
private const val DAYS_IN_WEEK = 7
private const val DEFAULT_WEATHER_CODE = 100