package com.ponkratov.weatherapp.data.di

import com.ponkratov.weatherapp.data.api.CitiesApi
import com.ponkratov.weatherapp.data.api.WeatherApi
import okhttp3.OkHttpClient
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

private const val BASE_URL_CITIES = "https://geocoding-api.open-meteo.com/v1/"
private const val BASE_URL_WEATHER = "https://api.open-meteo.com/v1/"

val citiesNetworkModule = module {
    single(qualifier(NetworkApiQualifier.CITIES)) {
        OkHttpClient.Builder().build()
    }

    single(qualifier(NetworkApiQualifier.CITIES)) {
        Retrofit.Builder()
            .baseUrl(BASE_URL_CITIES)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get(qualifier(NetworkApiQualifier.CITIES)))
            .build()
    }

    single(qualifier(NetworkApiQualifier.CITIES)) {
        get<Retrofit>(qualifier(NetworkApiQualifier.CITIES)).create<CitiesApi>()
    }
}

val weatherNetworkModule = module {
    single(qualifier(NetworkApiQualifier.WEATHER)) {
        OkHttpClient.Builder().build()
    }

    single(qualifier(NetworkApiQualifier.WEATHER)) {
        Retrofit.Builder()
            .baseUrl(BASE_URL_WEATHER)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get(qualifier(NetworkApiQualifier.WEATHER)))
            .build()
    }

    single(qualifier(NetworkApiQualifier.WEATHER)) {
        get<Retrofit>(qualifier(NetworkApiQualifier.WEATHER)).create<WeatherApi>()
    }
}