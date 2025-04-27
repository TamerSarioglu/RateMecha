package com.tamersarioglu.ratemecha.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String = "",
    val username: String = "",
    val email: String = "",
    val fullName: String? = null,
    val createdAt: String = "",
    val updatedAt: String = ""
) : Parcelable