package com.ponkratov.weatherapp.data.di

import com.ponkratov.weatherapp.data.service.LocationService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val serviceModule = module {
    singleOf(::LocationService)
}