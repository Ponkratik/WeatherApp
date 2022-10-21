package com.ponkratov.weatherapp.presentation.di

import com.ponkratov.weatherapp.presentation.ui.favorites.FavoritesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.ponkratov.weatherapp.presentation.ui.findcity.CitiesListViewModel
import com.ponkratov.weatherapp.presentation.ui.settings.SettingsViewModel
import com.ponkratov.weatherapp.presentation.ui.weatherinfo.WeatherInfoViewModel

val viewModelModule = module {
    viewModelOf(::CitiesListViewModel)
    viewModelOf(::WeatherInfoViewModel)
    viewModelOf(::FavoritesListViewModel)
    viewModelOf(::SettingsViewModel)
}