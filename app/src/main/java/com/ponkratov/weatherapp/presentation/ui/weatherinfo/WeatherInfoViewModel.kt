package com.ponkratov.weatherapp.presentation.ui.weatherinfo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ponkratov.weatherapp.domain.model.City
import com.ponkratov.weatherapp.domain.model.Weather
import com.ponkratov.weatherapp.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

class WeatherInfoViewModel(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    val cityFlow = MutableStateFlow(City(
        2950159,
        "Berlin",
        "Germany",
        "DE",
        52.52437,
        13.41053,
        74,
        "Europe/Berlin"
    ))

    private var isLoading = false

    val dataFlow = cityFlow
        .flatMapLatest {
            networkFlow(
                cityFlow.value.latitude,
                cityFlow.value.longitude
            )
        }
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            replay = 1
        )

    private fun networkFlow(latitude: Double, longitude: Double): Flow<List<Weather>> {
        return cityFlow
            .filter { !isLoading }
            .onEach { isLoading = true }
            .map {
                getWeatherUseCase(latitude, longitude)
                    .fold(
                        onSuccess = { it },
                        onFailure = { emptyList() }
                    )
            }
            .onEach {
                isLoading = false
            }
    }
}