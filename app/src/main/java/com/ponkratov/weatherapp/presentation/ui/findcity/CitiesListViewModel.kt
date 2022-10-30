package com.ponkratov.weatherapp.presentation.ui.findcity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ponkratov.weatherapp.domain.model.City
import com.ponkratov.weatherapp.domain.model.Lce
import com.ponkratov.weatherapp.domain.usecase.AddCityToFavoritesUseCase
import com.ponkratov.weatherapp.domain.usecase.GetCitiesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CitiesListViewModel(
    private val getCitiesUseCase: GetCitiesUseCase,
    private val addCityToFavoritesUseCase: AddCityToFavoritesUseCase
) : ViewModel() {

    private val networkFlow = MutableSharedFlow<Unit>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val queryFlow = MutableStateFlow("")

    val lceFlow = queryFlow
        .map {
            Lce.Loading
        }
        .flatMapLatest { networkFlow(queryFlow.value) }
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            replay = 1
        )

    init {
        queryFlow.tryEmit("")
    }

    private fun networkFlow(query: String): Flow<Lce<List<City>>> {
        return networkFlow
            .onStart { emit(Unit) }
            .map {
                getCitiesUseCase(query)
                    .fold(
                        onSuccess = { Lce.Content(it) },
                        onFailure = { Lce.Error(Throwable("")) }
                    )
            }
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