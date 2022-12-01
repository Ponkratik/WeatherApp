package com.ponkratov.weatherapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ponkratov.weatherapp.data.model.CityEntity

@Dao
interface CityDao {

    @Query("SELECT * FROM CityEntity")
    suspend fun getCities(): List<CityEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: CityEntity)

    @Delete
    suspend fun deleteCity(city: CityEntity)

    @Query("DELETE FROM CityEntity")
    suspend fun clear()
}