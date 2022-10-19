package com.ponkratov.weatherapp.domain.model

data class City(
    var id: Long,
    var name: String,
    var country: String,
    var countryCode: String,
    var latitude: Double,
    var longitude: Double,
    var elevation: Long,
    var timezone: String
)
