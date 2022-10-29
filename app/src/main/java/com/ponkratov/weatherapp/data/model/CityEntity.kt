package com.ponkratov.weatherapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CityEntity(
    @PrimaryKey
    var id: Long,
    var name: String,
    var country: String,
    @ColumnInfo(name = "country_code")
    var countryCode: String,
    var latitude: Double,
    var longitude: Double,
    var elevation: Long,
    var timezone: String
)