package com.ponkratov.weatherapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CityEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val country: String,
    @ColumnInfo(name = "country_code")
    val countryCode: String,
    val latitude: Double,
    val longitude: Double,
    val elevation: Long,
    val timezone: String
)