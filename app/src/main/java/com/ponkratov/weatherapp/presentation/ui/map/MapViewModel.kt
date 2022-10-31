package com.ponkratov.weatherapp.presentation.ui.map

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ponkratov.weatherapp.data.service.LocationService
import com.ponkratov.weatherapp.domain.model.City
import com.ponkratov.weatherapp.domain.usecase.GetFavoritesCitiesUseCase
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

class MapViewModel(
    private val locationService: LocationService,
    private val getFavoritesCitiesUseCase: GetFavoritesCitiesUseCase
): ViewModel() {

    val locationFlow: Flow<Location> by locationService::locationFlow

    val startLocationFlow: Flow<Location> = flow {
        locationService.getLocation()?.let { emit(it) }
    }.shareIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        replay = 0
    )

    val databaseFlow = MutableSharedFlow<Unit>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val lceFlow = databaseFlow
        .flatMapLatest { databaseFlow() }
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            replay = 1
        )

    private fun databaseFlow(): Flow<List<City>> {
        return databaseFlow
            .onStart { emit(Unit) }
            .map {
                getFavoritesCitiesUseCase()
            }
    }
}