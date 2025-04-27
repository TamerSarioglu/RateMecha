package com.tamersarioglu.ratemecha.domain.repository

import com.tamersarioglu.ratemecha.domain.model.AuthResult
import com.tamersarioglu.ratemecha.domain.model.RegistrationParams
import com.tamersarioglu.ratemecha.domain.model.User
import com.tamersarioglu.ratemecha.domain.model.UserCredentials
import com.tamersarioglu.ratemecha.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun register(registrationParams: RegistrationParams): Flow<Resource<AuthResult>>
    fun login(credentials: UserCredentials): Flow<Resource<AuthResult>>
    fun validateToken(): Flow<Resource<User>>
    fun getLoggedInUser(): Flow<User?>
    suspend fun logout()
}