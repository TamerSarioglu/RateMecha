package com.tamersarioglu.ratemecha.data.remote.model

import com.google.gson.annotations.SerializedName

data class MechanicDto(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String,
    @SerializedName("address") val address: String,
    @SerializedName("city") val city: String,
    @SerializedName("state") val state: String,
    @SerializedName("zipCode") val zipCode: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("email") val email: String? = null,
    @SerializedName("website") val website: String? = null,
    @SerializedName("specialties") val specialties: List<String> = emptyList(),
    @SerializedName("operatingHours") val operatingHours: String? = null,
    @SerializedName("createdAt") val createdAt: String? = null,
    @SerializedName("updatedAt") val updatedAt: String? = null
)