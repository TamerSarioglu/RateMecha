package com.tamersarioglu.ratemecha.data.remote.model

import com.google.gson.annotations.SerializedName

data class ReviewDto(
    @SerializedName("id") val id: String? = null,
    @SerializedName("userId") val userId: String,
    @SerializedName("mechanicId") val mechanicId: String,
    @SerializedName("rating") val rating: Int,
    @SerializedName("comment") val comment: String,
    @SerializedName("serviceType") val serviceType: String? = null,
    @SerializedName("serviceDate") val serviceDate: String? = null,
    @SerializedName("pricePaid") val pricePaid: Double? = null,
    @SerializedName("priceRating") val priceRating: Int? = null,
    @SerializedName("qualityRating") val qualityRating: Int? = null,
    @SerializedName("serviceRating") val serviceRating: Int? = null,
    @SerializedName("images") val images: List<String> = emptyList(),
    @SerializedName("createdAt") val createdAt: String? = null,
    @SerializedName("updatedAt") val updatedAt: String? = null
)