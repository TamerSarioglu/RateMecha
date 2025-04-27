package com.tamersarioglu.ratemecha.data.repository

import com.tamersarioglu.ratemecha.data.remote.api.ReviewApi
import com.tamersarioglu.ratemecha.data.remote.model.ReviewDto
import com.tamersarioglu.ratemecha.domain.model.NewReviewParams
import com.tamersarioglu.ratemecha.domain.model.Review
import com.tamersarioglu.ratemecha.domain.repository.ReviewRepository
import com.tamersarioglu.ratemecha.util.Resource
import com.tamersarioglu.ratemecha.util.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReviewRepositoryImpl @Inject constructor(
    private val reviewApi: ReviewApi
) : ReviewRepository {

    override fun createReview(userId: String, reviewParams: NewReviewParams): Flow<Resource<Review>> {
        val reviewDto = ReviewDto(
            userId = userId,
            mechanicId = reviewParams.mechanicId,
            rating = reviewParams.rating,
            comment = reviewParams.comment,
            serviceType = reviewParams.serviceType,
            serviceDate = reviewParams.serviceDate,
            pricePaid = reviewParams.pricePaid,
            priceRating = reviewParams.priceRating,
            qualityRating = reviewParams.qualityRating,
            serviceRating = reviewParams.serviceRating,
            images = reviewParams.images
        )

        return safeApiCall { reviewApi.createReview(reviewDto) }
            .map { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val data = resource.data
                        val review = Review(
                            id = data.id ?: "",
                            userId = data.userId,
                            mechanicId = data.mechanicId,
                            username = "", // We don't have the username here
                            rating = data.rating,
                            comment = data.comment,
                            serviceType = data.serviceType,
                            serviceDate = data.serviceDate,
                            pricePaid = data.pricePaid,
                            priceRating = data.priceRating,
                            qualityRating = data.qualityRating,
                            serviceRating = data.serviceRating,
                            images = data.images,
                            createdAt = data.createdAt ?: "",
                            updatedAt = data.updatedAt ?: ""
                        )

                        Resource.Success(review)
                    }
                    is Resource.Error -> Resource.Error(resource.message, resource.code)
                    is Resource.Loading -> Resource.Loading
                }
            }
    }

    override fun getReviewById(id: String): Flow<Resource<Review>> {
        return safeApiCall { reviewApi.getReviewById(id) }
            .map { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val data = resource.data
                        val review = Review(
                            id = data.id,
                            userId = "", // Not available in ReviewWithUserDetailsDto
                            mechanicId = data.mechanicId,
                            username = data.username,
                            rating = data.rating,
                            comment = data.comment,
                            serviceType = data.serviceType,
                            serviceDate = data.serviceDate,
                            pricePaid = data.pricePaid,
                            priceRating = data.priceRating,
                            qualityRating = data.qualityRating,
                            serviceRating = data.serviceRating,
                            images = data.images,
                            createdAt = data.createdAt,
                            updatedAt = data.updatedAt
                        )

                        Resource.Success(review)
                    }
                    is Resource.Error -> Resource.Error(resource.message, resource.code)
                    is Resource.Loading -> Resource.Loading
                }
            }
    }

    override fun getReviewsByMechanicId(mechanicId: String): Flow<Resource<List<Review>>> {
        return safeApiCall { reviewApi.getReviewsByMechanicId(mechanicId) }
            .map { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val reviews = resource.data.map { reviewDto ->
                            Review(
                                id = reviewDto.id,
                                userId = "",
                                mechanicId = reviewDto.mechanicId,
                                username = reviewDto.username,
                                rating = reviewDto.rating,
                                comment = reviewDto.comment,
                                serviceType = reviewDto.serviceType,
                                serviceDate = reviewDto.serviceDate,
                                pricePaid = reviewDto.pricePaid,
                                priceRating = reviewDto.priceRating,
                                qualityRating = reviewDto.qualityRating,
                                serviceRating = reviewDto.serviceRating,
                                images = reviewDto.images,
                                createdAt = reviewDto.createdAt,
                                updatedAt = reviewDto.updatedAt
                            )
                        }

                        Resource.Success(reviews)
                    }
                    is Resource.Error -> Resource.Error(resource.message, resource.code)
                    is Resource.Loading -> Resource.Loading
                }
            }
    }

    override fun getUserReviews(): Flow<Resource<List<Review>>> {
        return safeApiCall { reviewApi.getUserReviews() }
            .map { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val reviews = resource.data.map { reviewDto ->
                            Review(
                                id = reviewDto.id ?: "",
                                userId = reviewDto.userId,
                                mechanicId = reviewDto.mechanicId,
                                username = "",
                                rating = reviewDto.rating,
                                comment = reviewDto.comment,
                                serviceType = reviewDto.serviceType,
                                serviceDate = reviewDto.serviceDate,
                                pricePaid = reviewDto.pricePaid,
                                priceRating = reviewDto.priceRating,
                                qualityRating = reviewDto.qualityRating,
                                serviceRating = reviewDto.serviceRating,
                                images = reviewDto.images,
                                createdAt = reviewDto.createdAt ?: "",
                                updatedAt = reviewDto.updatedAt ?: ""
                            )
                        }

                        Resource.Success(reviews)
                    }
                    is Resource.Error -> Resource.Error(resource.message, resource.code)
                    is Resource.Loading -> Resource.Loading
                }
            }
    }
}