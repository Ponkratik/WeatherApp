package com.ponkratov.weatherapp

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.ponkratov.weatherapp.data.di.*
import com.ponkratov.weatherapp.presentation.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@WeatherApp)
            modules(
                viewModelModule,
                citiesNetworkModule,
                weatherNetworkModule,
                useCaseModule,
                repositoryModule,
                databaseModule
            )
        }
    }
}