package com.ponkratov.weatherapp.data.di

import com.ponkratov.weatherapp.data.repository.CityRepositoryImpl
import com.ponkratov.weatherapp.domain.repository.CityRepository
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::CityRepositoryImpl) {
        bind<CityRepository>()
    }
}