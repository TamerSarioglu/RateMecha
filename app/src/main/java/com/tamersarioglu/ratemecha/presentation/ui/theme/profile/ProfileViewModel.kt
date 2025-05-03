package com.tamersarioglu.ratemecha.presentation.ui.theme.profile


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamersarioglu.ratemecha.domain.model.Review
import com.tamersarioglu.ratemecha.domain.model.User
import com.tamersarioglu.ratemecha.domain.usecase.authusecases.GetCurrentUserUseCase
import com.tamersarioglu.ratemecha.domain.usecase.authusecases.LogoutUseCase
import com.tamersarioglu.ratemecha.domain.usecase.reviewusecases.GetUserReviewsUseCase
import com.tamersarioglu.ratemecha.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getUserReviewsUseCase: GetUserReviewsUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _userState = MutableStateFlow(UserState())
    val userState: StateFlow<UserState> = _userState.asStateFlow()

    private val _reviewsState = MutableStateFlow(ReviewsState())
    val reviewsState: StateFlow<ReviewsState> = _reviewsState.asStateFlow()

    init {
        loadUserData()
        loadUserReviews()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            _userState.update { it.copy(isLoading = true, errorMessage = null) }

            getCurrentUserUseCase().collectLatest { user ->
                if (user != null) {
                    _userState.update {
                        it.copy(
                            isLoading = false,
                            user = user,
                            errorMessage = null
                        )
                    }
                } else {
                    _userState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "User not found. Please login again."
                        )
                    }
                }
            }
        }
    }

    private fun loadUserReviews() {
        viewModelScope.launch {
            _reviewsState.update { it.copy(isLoading = true, errorMessage = null) }

            getUserReviewsUseCase()
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

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }
}

data class UserState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class ReviewsState(
    val reviews: List<Review> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)