package com.tamersarioglu.ratemecha.domain.usecase.authusecases

import com.tamersarioglu.ratemecha.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() {
        authRepository.logout()
    }
}