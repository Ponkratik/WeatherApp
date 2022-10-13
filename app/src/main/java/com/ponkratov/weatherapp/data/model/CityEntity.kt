package com.ponkratov.weatherapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CityEntity(
    @PrimaryKey
    var id: Long? = null,
    var name: String? = null,
    var country: String? = null,
    @ColumnInfo(name = "country_code")
    var countryCode: String? = null,
    var latitude: Double? = null,
    var longitude: Double? = null,
    var elevation: Long? = null,
    var timezone: String? = null
)