package com.tamersarioglu.ratemecha.domain.usecase.authusecases

import com.tamersarioglu.ratemecha.domain.model.AuthResult
import com.tamersarioglu.ratemecha.domain.model.RegistrationParams
import com.tamersarioglu.ratemecha.domain.repository.AuthRepository
import com.tamersarioglu.ratemecha.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke (params: RegistrationParams): Flow<Resource<AuthResult>> {
        return authRepository.register(params)
    }
}