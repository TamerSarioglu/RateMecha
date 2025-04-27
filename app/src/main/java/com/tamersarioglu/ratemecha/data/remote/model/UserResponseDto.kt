package com.tamersarioglu.ratemecha.data.remote.model

import com.google.gson.annotations.SerializedName

data class UserResponseDto(
    @SerializedName("id") val id: String,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("fullName") val fullName: String?,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String
)