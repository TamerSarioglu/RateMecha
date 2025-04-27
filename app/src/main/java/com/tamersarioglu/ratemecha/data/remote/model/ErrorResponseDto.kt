package com.tamersarioglu.ratemecha.data.remote.model

import com.google.gson.annotations.SerializedName

data class ErrorResponseDto(
    @SerializedName("statusCode") val statusCode: Int,
    @SerializedName("message") val message: String
)