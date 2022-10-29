package com.ponkratov.weatherapp.data.di

import com.ponkratov.weatherapp.data.repository.CityRepositoryImpl
import com.ponkratov.weatherapp.data.repository.SharedPrefsRepositoryImpl
import com.ponkratov.weatherapp.data.repository.WeatherRepositoryImpl
import com.ponkratov.weatherapp.domain.repository.CityRepository
import com.ponkratov.weatherapp.domain.repository.SharedPrefsRepository
import com.ponkratov.weatherapp.domain.repository.WeatherRepository
import org.koin.core.qualifier.qualifier
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    single {
        CityRepositoryImpl(get(qualifier(NetworkApiQualifier.CITIES)), get())
    } bind CityRepository::class

    single {
        WeatherRepositoryImpl(get(qualifier(NetworkApiQualifier.WEATHER)))
    } bind WeatherRepository::class

    single {
        SharedPrefsRepositoryImpl(get(), get())
    } bind SharedPrefsRepository::class
}