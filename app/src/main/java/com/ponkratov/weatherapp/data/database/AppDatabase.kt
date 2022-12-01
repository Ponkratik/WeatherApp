package com.ponkratov.weatherapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ponkratov.weatherapp.data.model.CityEntity

@Database(entities = [CityEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract val cityDao: CityDao
}