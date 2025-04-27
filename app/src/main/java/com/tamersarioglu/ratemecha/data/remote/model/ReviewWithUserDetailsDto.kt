package com.tamersarioglu.ratemecha.data.remote.model

import com.google.gson.annotations.SerializedName

data class ReviewWithUserDetailsDto(
    @SerializedName("id") val id: String,
    @SerializedName("mechanicId") val mechanicId: String,
    @SerializedName("username") val username: String,
    @SerializedName("rating") val rating: Int,
    @SerializedName("comment") val comment: String,
    @SerializedName("serviceType") val serviceType: String?,
    @SerializedName("serviceDate") val serviceDate: String?,
    @SerializedName("pricePaid") val pricePaid: Double?,
    @SerializedName("priceRating") val priceRating: Int?,
    @SerializedName("qualityRating") val qualityRating: Int?,
    @SerializedName("serviceRating") val serviceRating: Int?,
    @SerializedName("images") val images: List<String>,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String
)