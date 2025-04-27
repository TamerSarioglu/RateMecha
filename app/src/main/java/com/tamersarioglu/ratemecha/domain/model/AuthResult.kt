package com.tamersarioglu.ratemecha.domain.model

data class AuthResult(
    val token: String,
    val user: User
)