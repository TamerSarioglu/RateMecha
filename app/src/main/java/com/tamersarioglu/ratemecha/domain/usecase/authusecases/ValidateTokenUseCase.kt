package com.tamersarioglu.ratemecha.domain.usecase.authusecases

import com.tamersarioglu.ratemecha.domain.model.User
import com.tamersarioglu.ratemecha.domain.repository.AuthRepository
import com.tamersarioglu.ratemecha.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ValidateTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<Resource<User>> {
        return authRepository.validateToken()
    }
}
