package com.tamersarioglu.ratemecha.data.remote.model

import com.google.gson.annotations.SerializedName

data class AuthResponseDto(
    @SerializedName("token") val token: String,
    @SerializedName("user") val user: UserResponseDto
)