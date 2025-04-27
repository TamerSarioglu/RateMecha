package com.tamersarioglu.ratemecha.domain.usecase.reviewusecases

import com.tamersarioglu.ratemecha.domain.model.Review
import com.tamersarioglu.ratemecha.domain.repository.ReviewRepository
import com.tamersarioglu.ratemecha.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReviewsByMechanicIdUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
) {
    operator fun invoke(mechanicId: String): Flow<Resource<List<Review>>> {
        return reviewRepository.getReviewsByMechanicId(mechanicId)
    }
}