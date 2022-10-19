package com.ponkratov.weatherapp.data.di

import androidx.room.Room
import org.koin.dsl.module
import com.ponkratov.weatherapp.data.database.AppDatabase

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "database"
        ).build()
    }

    single { get<AppDatabase>().cityDao }
}