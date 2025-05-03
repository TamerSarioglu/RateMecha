package com.tamersarioglu.ratemecha.presentation.ui.theme.review


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamersarioglu.ratemecha.domain.model.NewReviewParams
import com.tamersarioglu.ratemecha.domain.usecase.authusecases.GetCurrentUserUseCase
import com.tamersarioglu.ratemecha.domain.usecase.reviewusecases.CreateReviewUseCase
import com.tamersarioglu.ratemecha.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddReviewViewModel @Inject constructor(
    private val createReviewUseCase: CreateReviewUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AddReviewState())
    val state: StateFlow<AddReviewState> = _state.asStateFlow()

    fun initialize(mechanicId: String) {
        _state.update { it.copy(mechanicId = mechanicId) }
    }

    fun onRatingChange(rating: Int) {
        _state.update { it.copy(rating = rating) }
    }

    fun onCommentChange(comment: String) {
        _state.update { it.copy(comment = comment) }
    }

    fun onServiceTypeChange(serviceType: String) {
        _state.update { it.copy(serviceType = serviceType) }
    }

    fun onServiceDateChange(serviceDate: String) {
        _state.update { it.copy(serviceDate = serviceDate) }
    }

    fun onPricePaidChange(pricePaid: Double) {
        _state.update { it.copy(pricePaid = pricePaid) }
    }

    fun onPriceRatingChange(priceRating: Int) {
        _state.update { it.copy(priceRating = priceRating) }
    }

    fun onQualityRatingChange(qualityRating: Int) {
        _state.update { it.copy(qualityRating = qualityRating) }
    }

    fun onServiceRatingChange(serviceRating: Int) {
        _state.update { it.copy(serviceRating = serviceRating) }
    }

    fun submitReview() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            val currentUser = getCurrentUserUseCase().firstOrNull()

            if (currentUser == null) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "You must be logged in to submit a review"
                    )
                }
                return@launch
            }

            val reviewParams = NewReviewParams(
                mechanicId = state.value.mechanicId,
                rating = state.value.rating,
                comment = state.value.comment,
                serviceType = state.value.serviceType.takeIf { it.isNotBlank() },
                serviceDate = state.value.serviceDate.takeIf { it.isNotBlank() },
                pricePaid = if (state.value.pricePaid > 0) state.value.pricePaid else null,
                priceRating = if (state.value.priceRating > 0) state.value.priceRating else null,
                qualityRating = if (state.value.qualityRating > 0) state.value.qualityRating else null,
                serviceRating = if (state.value.serviceRating > 0) state.value.serviceRating else null
            )

            createReviewUseCase(currentUser.id, reviewParams)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    isSuccess = true,
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

data class AddReviewState(
    val mechanicId: String = "",
    val rating: Int = 0,
    val comment: String = "",
    val serviceType: String = "",
    val serviceDate: String = "",
    val pricePaid: Double = 0.0,
    val priceRating: Int = 0,
    val qualityRating: Int = 0,
    val serviceRating: Int = 0,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)