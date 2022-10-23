package com.ponkratov.weatherapp.presentation.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ponkratov.weatherapp.domain.model.City
import com.ponkratov.weatherapp.domain.usecase.GetFavoritesCitiesUseCase
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

class MapViewModel(
    private val getFavoritesCitiesUseCase: GetFavoritesCitiesUseCase
): ViewModel() {

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