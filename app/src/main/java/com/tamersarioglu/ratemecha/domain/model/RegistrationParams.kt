package com.tamersarioglu.ratemecha.domain.model

data class RegistrationParams(
    val username: String,
    val email: String,
    val password: String,
    val fullName: String? = null
)