package com.ponkratov.weatherapp.presentation.ui.findcity

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ponkratov.weatherapp.domain.model.City
import com.ponkratov.weatherapp.domain.usecase.AddCityToFavoritesUseCase
import com.ponkratov.weatherapp.domain.usecase.GetCitiesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CitiesListViewModel(
    private val getCitiesUseCase: GetCitiesUseCase,
    private val addCityToFavoritesUseCase: AddCityToFavoritesUseCase
) : ViewModel() {

    private val refreshFlow = MutableSharedFlow<Unit>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val networkFlow = MutableSharedFlow<Unit>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private var isLoading = false

    private var queryFlow = MutableStateFlow("")

    val dataFlow = queryFlow
        .flatMapLatest { networkFlow(queryFlow.value) }
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            replay = 1
        )

    private fun networkFlow(query: String): Flow<List<City>> {
        return networkFlow
            .onStart { emit(Unit) }
            .filter { !isLoading }
            .onEach { isLoading = true }
            .map {
                getCitiesUseCase(query)
                    .fold(
                        onSuccess = { it },
                        onFailure = { emptyList() }
                    )
            }
            .onEach { isLoading = false }
    }

    fun onQueryTextChanged(query: String) {
        queryFlow.tryEmit(query)
    }

    fun onCheckboxFavoritesCheckedChanged(item: City, checked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            addCityToFavoritesUseCase(item)
        }
    }
}