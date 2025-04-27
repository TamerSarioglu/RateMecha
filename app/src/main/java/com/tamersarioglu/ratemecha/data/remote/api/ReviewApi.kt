package com.tamersarioglu.ratemecha.data.remote.api

import com.tamersarioglu.ratemecha.data.remote.model.ReviewDto
import com.tamersarioglu.ratemecha.data.remote.model.ReviewWithUserDetailsDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewApi {
    @POST("reviews")
    suspend fun createReview(@Body review: ReviewDto): Response<ReviewDto>

    @GET("reviews/{id}")
    suspend fun getReviewById(@Path("id") id: String): Response<ReviewWithUserDetailsDto>

    @GET("reviews/mechanic/{mechanicId}")
    suspend fun getReviewsByMechanicId(@Path("mechanicId") mechanicId: String): Response<List<ReviewWithUserDetailsDto>>

    @GET("reviews/user")
    suspend fun getUserReviews(): Response<List<ReviewDto>>
}