package com.tamersarioglu.ratemecha.domain.usecase.authusecases

import com.tamersarioglu.ratemecha.domain.model.User
import com.tamersarioglu.ratemecha.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<User?> {
        return authRepository.getLoggedInUser()
    }
}