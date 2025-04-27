package com.tamersarioglu.ratemecha.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
    val id: String = "",
    val userId: String = "",
    val mechanicId: String = "",
    val username: String = "",
    val rating: Int = 0,
    val comment: String = "",
    val serviceType: String? = null,
    val serviceDate: String? = null,
    val pricePaid: Double? = null,
    val priceRating: Int? = null,
    val qualityRating: Int? = null,
    val serviceRating: Int? = null,
    val images: List<String> = emptyList(),
    val createdAt: String = "",
    val updatedAt: String = ""
) : Parcelable