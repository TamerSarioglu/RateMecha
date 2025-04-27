package com.tamersarioglu.ratemecha.data.remote.model

import com.google.gson.annotations.SerializedName

data class MechanicWithRatingDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("address") val address: String,
    @SerializedName("city") val city: String,
    @SerializedName("state") val state: String,
    @SerializedName("zipCode") val zipCode: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("email") val email: String?,
    @SerializedName("website") val website: String?,
    @SerializedName("specialties") val specialties: List<String>,
    @SerializedName("operatingHours") val operatingHours: String?,
    @SerializedName("averageRating") val averageRating: Double,
    @SerializedName("totalReviews") val totalReviews: Int,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String
)