package com.ponkratov.weatherapp.domain.di

import com.ponkratov.weatherapp.domain.usecase.*
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val useCaseModule = module {
    singleOf(::AddCityToFavoritesUseCase)
    singleOf(::GetCitiesUseCase)
    singleOf(::GetWeatherUseCase)
    singleOf(::GetFavoritesCitiesUseCase)
    singleOf(::RemoveCityFromFavoritesUseCase)
    singleOf(::GetThemeCodeUseCase)
    singleOf(::SetThemeCodeUseCase)
    singleOf(::GetLanguageCodeUseCase)
    singleOf(::SetLanguageCodeUseCase)
}