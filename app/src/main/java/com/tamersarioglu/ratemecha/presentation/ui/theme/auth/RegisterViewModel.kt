package com.tamersarioglu.ratemecha.presentation.ui.theme.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamersarioglu.ratemecha.domain.model.RegistrationParams
import com.tamersarioglu.ratemecha.domain.usecase.authusecases.RegisterUseCase
import com.tamersarioglu.ratemecha.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    fun onUsernameChange(username: String) {
        _state.update { it.copy(username = username) }
    }

    fun onEmailChange(email: String) {
        _state.update { it.copy(email = email) }
    }

    fun onPasswordChange(password: String) {
        _state.update { it.copy(password = password) }
    }

    fun onFullNameChange(fullName: String) {
        _state.update { it.copy(fullName = fullName) }
    }

    fun register() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            val registrationParams = RegistrationParams(
                username = state.value.username,
                email = state.value.email,
                password = state.value.password,
                fullName = state.value.fullName.takeIf { it.isNotBlank() }
            )

            registerUseCase(registrationParams)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    isRegistered = true,
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

data class RegisterState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val fullName: String = "",
    val isLoading: Boolean = false,
    val isRegistered: Boolean = false,
    val errorMessage: String? = null
)