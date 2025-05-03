package com.tamersarioglu.ratemecha

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamersarioglu.ratemecha.domain.model.User
import com.tamersarioglu.ratemecha.domain.usecase.authusecases.GetCurrentUserUseCase
import com.tamersarioglu.ratemecha.domain.usecase.authusecases.ValidateTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val validateTokenUseCase: ValidateTokenUseCase
) : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    val isLoggedIn = currentUser.map { it != null }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5000),
            initialValue = false
        )

    init {
        viewModelScope.launch {
            getCurrentUserUseCase().collect { user ->
                _currentUser.value = user

                if (user != null) {
                    validateTokenUseCase()
                        .catch { /* Token validation failed, but we'll keep the user logged in until they try to perform an action */ }
                        .collect()
                }
            }
        }
    }
}