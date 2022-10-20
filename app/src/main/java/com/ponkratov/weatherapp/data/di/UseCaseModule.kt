package com.ponkratov.weatherapp.data.di

import com.ponkratov.weatherapp.domain.usecase.AddCityToFavoritesUseCase
import com.ponkratov.weatherapp.domain.usecase.GetCitiesUseCase
import com.ponkratov.weatherapp.domain.usecase.GetWeatherUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val useCaseModule = module {
    singleOf(::AddCityToFavoritesUseCase)
    singleOf(::GetCitiesUseCase)
    singleOf(::GetWeatherUseCase)
}