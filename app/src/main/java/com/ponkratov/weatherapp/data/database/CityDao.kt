package com.ponkratov.weatherapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ponkratov.weatherapp.data.model.CityEntity

@Dao
interface CityDao {

    @Query("SELECT * FROM CityEntity")
    suspend fun getCities(): List<CityEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCities(vararg cities: CityEntity)

    @Query("DELETE FROM CityEntity")
    suspend fun clear()
}