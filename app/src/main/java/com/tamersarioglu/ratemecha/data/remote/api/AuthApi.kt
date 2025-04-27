package com.tamersarioglu.ratemecha.data.remote.api


import com.tamersarioglu.ratemecha.data.remote.model.AuthResponseDto
import com.tamersarioglu.ratemecha.data.remote.model.UserCredentialsDto
import com.tamersarioglu.ratemecha.data.remote.model.UserDto
import com.tamersarioglu.ratemecha.data.remote.model.UserResponseDto
import retrofit2.Response
import retrofit2.http.*

interface AuthApi {
    @POST("auth/register")
    suspend fun register(@Body user: UserDto): Response<AuthResponseDto>

    @POST("auth/login")
    suspend fun login(@Body credentials: UserCredentialsDto): Response<AuthResponseDto>

    @GET("auth/validate")
    suspend fun validateToken(): Response<UserResponseDto>
}