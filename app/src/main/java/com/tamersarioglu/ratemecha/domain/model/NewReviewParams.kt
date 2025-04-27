package com.tamersarioglu.ratemecha.domain.model

data class NewReviewParams(
    val mechanicId: String,
    val rating: Int,
    val comment: String,
    val serviceType: String? = null,
    val serviceDate: String? = null,
    val pricePaid: Double? = null,
    val priceRating: Int? = null,
    val qualityRating: Int? = null,
    val serviceRating: Int? = null,
    val images: List<String> = emptyList()
)