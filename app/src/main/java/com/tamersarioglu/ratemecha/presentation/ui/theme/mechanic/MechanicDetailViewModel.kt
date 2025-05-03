package com.tamersarioglu.ratemecha.presentation.ui.theme.mechanic


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamersarioglu.ratemecha.domain.model.Mechanic
import com.tamersarioglu.ratemecha.domain.model.Review
import com.tamersarioglu.ratemecha.domain.usecase.mechanicusecases.GetMechanicByIdUseCase
import com.tamersarioglu.ratemecha.domain.usecase.reviewusecases.GetReviewsByMechanicIdUseCase
import com.tamersarioglu.ratemecha.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MechanicDetailViewModel @Inject constructor(
    private val getMechanicByIdUseCase: GetMechanicByIdUseCase,
    private val getReviewsByMechanicIdUseCase: GetReviewsByMechanicIdUseCase
) : ViewModel() {

    private val _mechanicState = MutableStateFlow(MechanicState())
    val mechanicState: StateFlow<MechanicState> = _mechanicState.asStateFlow()

    private val _reviewsState = MutableStateFlow(ReviewsState())
    val reviewsState: StateFlow<ReviewsState> = _reviewsState.asStateFlow()

    fun loadMechanic(mechanicId: String) {
        viewModelScope.launch {
            _mechanicState.update { it.copy(isLoading = true, errorMessage = null) }

            getMechanicByIdUseCase(mechanicId)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _mechanicState.update {
                                it.copy(
                                    isLoading = false,
                                    mechanic = result.data,
                                    errorMessage = null
                                )
                            }
                        }
                        is Resource.Error -> {
                            _mechanicState.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = result.message
                                )
                            }
                        }
                        is Resource.Loading -> {
                            _mechanicState.update { it.copy(isLoading = true) }
                        }
                    }
                }
        }
    }

    fun loadReviews(mechanicId: String) {
        viewModelScope.launch {
            _reviewsState.update { it.copy(isLoading = true, errorMessage = null) }

            getReviewsByMechanicIdUseCase(mechanicId)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _reviewsState.update {
                                it.copy(
                                    isLoading = false,
                                    reviews = result.data,
                                    errorMessage = null
                                )
                            }
                        }
                        is Resource.Error -> {
                            _reviewsState.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = result.message
                                )
                            }
                        }
                        is Resource.Loading -> {
                            _reviewsState.update { it.copy(isLoading = true) }
                        }
                    }
                }
        }
    }
}

data class MechanicState(
    val mechanic: Mechanic? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class ReviewsState(
    val reviews: List<Review> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)