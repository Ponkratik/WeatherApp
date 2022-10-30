package com.ponkratov.weatherapp.presentation.ui.weatherinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ponkratov.weatherapp.domain.model.City
import com.ponkratov.weatherapp.domain.model.Weather
import com.ponkratov.weatherapp.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.flow.*

class WeatherInfoViewModel(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val city: City
) : ViewModel() {

    private val cityFlow = MutableStateFlow(city)

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