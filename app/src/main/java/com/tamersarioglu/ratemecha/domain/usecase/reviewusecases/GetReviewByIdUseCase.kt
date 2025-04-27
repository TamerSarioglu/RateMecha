package com.tamersarioglu.ratemecha.domain.usecase.reviewusecases

import com.tamersarioglu.ratemecha.domain.model.Review
import com.tamersarioglu.ratemecha.domain.repository.ReviewRepository
import com.tamersarioglu.ratemecha.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReviewByIdUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
) {
    operator fun invoke(id: String): Flow<Resource<Review>> {
        return reviewRepository.getReviewById(id)
    }
}