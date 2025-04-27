package com.tamersarioglu.ratemecha.domain.usecase.authusecases

import com.tamersarioglu.ratemecha.domain.model.UserCredentials
import com.tamersarioglu.ratemecha.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(email: String, password: String) =
        authRepository.login(credentials = UserCredentials(username = email, password = password))
}