package com.ponkratov.weatherapp.data.di

import com.ponkratov.weatherapp.data.repository.CityRepositoryImpl
import com.ponkratov.weatherapp.data.repository.SharedPrefsRepositoryImpl
import com.ponkratov.weatherapp.data.repository.WeatherRepositoryImpl
import com.ponkratov.weatherapp.domain.repository.CityRepository
import com.ponkratov.weatherapp.domain.repository.SharedPrefsRepository
import com.ponkratov.weatherapp.domain.repository.WeatherRepository
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.named
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    single {
        CityRepositoryImpl(get(named("ApiCities")), get())
    } bind CityRepository::class

    single {
        WeatherRepositoryImpl(get(named("ApiWeather")))
    } bind WeatherRepository::class

    single {
        SharedPrefsRepositoryImpl(get())
    } bind SharedPrefsRepository::class
}