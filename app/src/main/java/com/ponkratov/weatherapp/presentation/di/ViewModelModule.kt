package com.ponkratov.weatherapp.presentation.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.ponkratov.weatherapp.presentation.ui.findcity.CitiesListViewModel

val viewModelModule = module {
    viewModelOf(::CitiesListViewModel)
}