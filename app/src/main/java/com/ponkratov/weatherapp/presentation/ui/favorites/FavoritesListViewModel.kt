package com.ponkratov.weatherapp.presentation.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ponkratov.weatherapp.domain.model.City
import com.ponkratov.weatherapp.domain.usecase.GetFavoritesCitiesUseCase
import com.ponkratov.weatherapp.domain.usecase.RemoveCityFromFavoritesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoritesListViewModel(
    private val getFavoritesCitiesUseCase: GetFavoritesCitiesUseCase,
    private val removeCityFromFavoritesUseCase: RemoveCityFromFavoritesUseCase
): ViewModel() {

    private var queryFlow = MutableStateFlow("")

    private val databaseFlow = MutableSharedFlow<Unit>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val lceFlow = queryFlow
        .flatMapLatest { databaseFlow(queryFlow.value) }
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            replay = 1
        )

    init {
        databaseFlow.tryEmit(Unit)
    }

    private fun databaseFlow(query: String): Flow<List<City>> {
        return databaseFlow
            .onStart { emit(Unit) }
            .map {
                getFavoritesCitiesUseCase()
                    .filter { it.name.lowercase().contains(query.lowercase()) }
            }
    }

    fun onCheckboxFavoritesCheckedChanged(city: City, checked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            removeCityFromFavoritesUseCase(city)
            databaseFlow.tryEmit(Unit)
        }
    }

    fun onQueryTextChanged(query: String) {
        queryFlow.tryEmit(query)
    }
}