package com.tamersarioglu.ratemecha.data.remote.model

import com.google.gson.annotations.SerializedName

data class UserCredentialsDto(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)