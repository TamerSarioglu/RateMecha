package com.tamersarioglu.ratemecha.presentation.ui.theme.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamersarioglu.ratemecha.domain.model.Mechanic
import com.tamersarioglu.ratemecha.domain.usecase.mechanicusecases.SearchMechanicsUseCase
import com.tamersarioglu.ratemecha.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val searchMechanicsUseCase: SearchMechanicsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private var searchJob: Job? = null

    init {
        search()
    }

    fun onSearchQueryChange(query: String) {
        _state.update { it.copy(searchQuery = query) }

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500) // Debounce
            search()
        }
    }

    fun onCitySelected(city: String?) {
        _state.update { it.copy(selectedCity = city) }
        search()
    }

    fun onStateSelected(state: String?) {
        _state.update { it.copy(selectedState = state) }
        search()
    }

    fun onSpecialtySelected(specialty: String?) {
        _state.update { it.copy(selectedSpecialty = specialty) }
        search()
    }

    fun search() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            searchMechanicsUseCase(
                query = state.value.searchQuery.takeIf { it.isNotBlank() },
                city = state.value.selectedCity,
                state = state.value.selectedState,
                specialty = state.value.selectedSpecialty
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                mechanics = result.data,
                                errorMessage = null
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = result.message
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }
}

data class HomeState(
    val searchQuery: String = "",
    val selectedCity: String? = null,
    val selectedState: String? = null,
    val selectedSpecialty: String? = null,
    val mechanics: List<Mechanic> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)