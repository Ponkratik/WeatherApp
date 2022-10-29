package com.ponkratov.weatherapp.data.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.ponkratov.weatherapp.data.sharedprefs.DarkModeSharedPrefs
import com.ponkratov.weatherapp.data.sharedprefs.LanguageSharedPrefs
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val sharedPrefsModule = module {
    single {
        provideSharedPref(get())
    }

    singleOf(::DarkModeSharedPrefs)
    singleOf(::LanguageSharedPrefs)
}

fun provideSharedPref(app: Application): SharedPreferences {
    return app.applicationContext.getSharedPreferences(
        SHARED_PREFERENCE_NAME,
        Context.MODE_PRIVATE
    )
}

private const val SHARED_PREFERENCE_NAME = "weather_app_shared_prefs"

