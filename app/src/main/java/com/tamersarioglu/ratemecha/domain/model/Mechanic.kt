package com.tamersarioglu.ratemecha.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Mechanic(
    val id: String = "",
    val name: String = "",
    val address: String = "",
    val city: String = "",
    val state: String = "",
    val zipCode: String = "",
    val phone: String = "",
    val email: String? = null,
    val website: String? = null,
    val specialties: List<String> = emptyList(),
    val operatingHours: String? = null,
    val averageRating: Double = 0.0,
    val totalReviews: Int = 0,
    val createdAt: String = "",
    val updatedAt: String = ""
) : Parcelable