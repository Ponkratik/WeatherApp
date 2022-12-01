package com.ponkratov.weatherapp

import android.app.Application
import android.content.Context
import com.ponkratov.weatherapp.data.di.*
import com.ponkratov.weatherapp.domain.di.useCaseModule
import com.ponkratov.weatherapp.domain.service.LanguageService
import com.ponkratov.weatherapp.presentation.di.viewModelModule
import com.ponkratov.weatherapp.presentation.service.LanguageAwareContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeatherApp : Application() {

    private val languageService by inject<LanguageService>()

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LanguageAwareContext(base, application = this))
    }

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
                databaseModule,
                serviceModule
            )
        }

        languageService
            .languageFlow
            .onEach {
                (baseContext as LanguageAwareContext).appLanguageCode = it
            }
            .launchIn(CoroutineScope(Dispatchers.Main))
    }
}