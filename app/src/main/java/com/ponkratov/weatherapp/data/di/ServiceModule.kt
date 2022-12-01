package com.ponkratov.weatherapp.data.di

import com.ponkratov.weatherapp.data.service.LocationService
import com.ponkratov.weatherapp.data.service.PreferencesService
import com.ponkratov.weatherapp.domain.service.LanguageService
import com.ponkratov.weatherapp.domain.service.ThemeService
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val serviceModule = module {
    singleOf(::PreferencesService) {
        bind<ThemeService>()
        bind<LanguageService>()
    }
    singleOf(::LocationService)
}