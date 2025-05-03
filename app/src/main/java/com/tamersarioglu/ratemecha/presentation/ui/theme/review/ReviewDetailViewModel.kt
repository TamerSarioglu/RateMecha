package com.tamersarioglu.ratemecha.presentation.ui.theme.review


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamersarioglu.ratemecha.domain.model.Review
import com.tamersarioglu.ratemecha.domain.usecase.reviewusecases.GetReviewByIdUseCase
import com.tamersarioglu.ratemecha.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewDetailViewModel @Inject constructor(
    private val getReviewByIdUseCase: GetReviewByIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ReviewDetailState())
    val state: StateFlow<ReviewDetailState> = _state.asStateFlow()

    fun loadReview(reviewId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            getReviewByIdUseCase(reviewId)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    review = result.data,
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

data class ReviewDetailState(
    val review: Review? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)