package com.ponkratov.weatherapp.data.di

import com.ponkratov.weatherapp.data.api.CitiesApi
import com.ponkratov.weatherapp.data.api.WeatherApi
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

private const val BASE_URL_CITIES = "https://geocoding-api.open-meteo.com/v1/"
private const val BASE_URL_WEATHER = "https://api.open-meteo.com/v1/"

val networkModule = module {
    single { OkHttpClient.Builder().build() }

    single {
        named("RetrofitCities")
        Retrofit.Builder()
            .baseUrl(BASE_URL_CITIES)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single {
        named("ApiCities")
        get<Retrofit>().create<CitiesApi>()
    }

    /*single {
        named("RetrofitWeather")
        Retrofit.Builder()
            .baseUrl(BASE_URL_WEATHER)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single {
        named("ApiWeather")
        get<Retrofit>().create<WeatherApi>()
    }*/
}