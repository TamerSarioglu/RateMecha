package com.tamersarioglu.ratemecha.data.repository

import com.tamersarioglu.ratemecha.data.remote.api.AuthApi
import com.tamersarioglu.ratemecha.data.remote.model.UserCredentialsDto
import com.tamersarioglu.ratemecha.data.remote.model.UserDto
import com.tamersarioglu.ratemecha.domain.model.AuthResult
import com.tamersarioglu.ratemecha.domain.model.RegistrationParams
import com.tamersarioglu.ratemecha.domain.model.User
import com.tamersarioglu.ratemecha.domain.model.UserCredentials
import com.tamersarioglu.ratemecha.domain.repository.AuthRepository
import com.tamersarioglu.ratemecha.util.Resource
import com.tamersarioglu.ratemecha.util.TokenManager
import com.tamersarioglu.ratemecha.util.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val tokenManager: TokenManager
): AuthRepository {
    override fun register(registrationParams: RegistrationParams): Flow<Resource<AuthResult>> {
        val userDto = UserDto(
            username = registrationParams.username,
            email = registrationParams.email,
            password = registrationParams.password,
            fullName = registrationParams.fullName
        )

        return safeApiCall { authApi.register(user = userDto) }
            .map { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val data = resource.data
                        val user = User(
                            id = data.user.id,
                            username = data.user.username,
                            email = data.user.email,
                            fullName = data.user.fullName,
                            createdAt = data.user.createdAt,
                            updatedAt = data.user.updatedAt
                        )

                        Resource.Success(AuthResult(token = data.token, user = user))
                    }
                    is Resource.Error -> Resource.Error(resource.message, resource.code)
                    is Resource.Loading -> Resource.Loading
                }
            }
            .onEach { resource ->
                if (resource is Resource.Success) {
                    tokenManager.saveToken(resource.data.token)
                    tokenManager.saveUserInfo(
                        userId = resource.data.user.id,
                        username = resource.data.user.username,
                        email = resource.data.user.email,
                        fullName = resource.data.user.fullName ?: ""
                    )
                }
            }
    }

    override fun login(credentials: UserCredentials): Flow<Resource<AuthResult>> {
        val credentialsDto = UserCredentialsDto(
            username = credentials.username,
            password = credentials.password
        )

        return safeApiCall { authApi.login(credentialsDto) }
            .map { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val data = resource.data
                        val user = User(
                            id = data.user.id,
                            username = data.user.username,
                            email = data.user.email,
                            fullName = data.user.fullName,
                            createdAt = data.user.createdAt,
                            updatedAt = data.user.updatedAt
                        )

                        Resource.Success(AuthResult(token = data.token, user = user))
                    }
                    is Resource.Error -> Resource.Error(resource.message, resource.code)
                    is Resource.Loading -> Resource.Loading
                }
            }
            .onEach { resource ->
                if (resource is Resource.Success) {
                    tokenManager.saveToken(resource.data.token)
                    tokenManager.saveUserInfo(
                        userId = resource.data.user.id,
                        username = resource.data.user.username,
                        email = resource.data.user.email,
                        fullName = resource.data.user.fullName ?: ""
                    )
                }
            }
    }

    override fun validateToken(): Flow<Resource<User>> {
        return safeApiCall { authApi.validateToken() }
            .map { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val data = resource.data
                        val user = User(
                            id = data.id,
                            username = data.username,
                            email = data.email,
                            fullName = data.fullName,
                            createdAt = data.createdAt,
                            updatedAt = data.updatedAt
                        )

                        Resource.Success(user)
                    }
                    is Resource.Error -> Resource.Error(resource.message, resource.code)
                    is Resource.Loading -> Resource.Loading
                }
            }
    }

    override fun getLoggedInUser(): Flow<User?> {
        return tokenManager.userInfo.map { userInfo ->
            userInfo?.let {
                User(
                    id = it.id,
                    username = it.username,
                    email = it.email,
                    fullName = it.fullName
                )
            }
        }
    }

    override suspend fun logout() {
        tokenManager.clearAll()
    }
}