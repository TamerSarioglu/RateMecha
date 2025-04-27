package com.tamersarioglu.ratemecha.domain.repository

import com.tamersarioglu.ratemecha.domain.model.NewReviewParams
import com.tamersarioglu.ratemecha.domain.model.Review
import com.tamersarioglu.ratemecha.util.Resource
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {
    fun createReview(userId: String, reviewParams: NewReviewParams): Flow<Resource<Review>>
    fun getReviewById(id: String): Flow<Resource<Review>>
    fun getReviewsByMechanicId(mechanicId: String): Flow<Resource<List<Review>>>
    fun getUserReviews(): Flow<Resource<List<Review>>>
}