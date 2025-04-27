package com.tamersarioglu.ratemecha.domain.usecase.reviewusecases

import com.tamersarioglu.ratemecha.domain.model.NewReviewParams
import com.tamersarioglu.ratemecha.domain.model.Review
import com.tamersarioglu.ratemecha.domain.repository.ReviewRepository
import com.tamersarioglu.ratemecha.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateReviewUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
) {
    operator fun invoke(userId: String, reviewParams: NewReviewParams): Flow<Resource<Review>> {
        return reviewRepository.createReview(userId, reviewParams)
    }
}
