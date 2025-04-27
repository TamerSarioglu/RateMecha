package com.tamersarioglu.ratemecha.data.repository

import com.tamersarioglu.ratemecha.data.remote.api.MechanicApi
import com.tamersarioglu.ratemecha.domain.model.Mechanic
import com.tamersarioglu.ratemecha.domain.model.SearchMechanicParams
import com.tamersarioglu.ratemecha.domain.repository.MechanicRepository
import com.tamersarioglu.ratemecha.util.Resource
import com.tamersarioglu.ratemecha.util.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MechanicRepositoryImpl @Inject constructor(
    private val mechanicApi: MechanicApi
) : MechanicRepository {

    override fun getMechanicById(id: String): Flow<Resource<Mechanic>> {
        return safeApiCall { mechanicApi.getMechanicById(id) }
            .map { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val data = resource.data
                        val mechanic = Mechanic(
                            id = data.id,
                            name = data.name,
                            address = data.address,
                            city = data.city,
                            state = data.state,
                            zipCode = data.zipCode,
                            phone = data.phone,
                            email = data.email,
                            website = data.website,
                            specialties = data.specialties,
                            operatingHours = data.operatingHours,
                            averageRating = data.averageRating,
                            totalReviews = data.totalReviews,
                            createdAt = data.createdAt,
                            updatedAt = data.updatedAt
                        )

                        Resource.Success(mechanic)
                    }
                    is Resource.Error -> Resource.Error(resource.message, resource.code)
                    is Resource.Loading -> Resource.Loading
                }
            }
    }

    override fun searchMechanics(params: SearchMechanicParams): Flow<Resource<List<Mechanic>>> {
        return safeApiCall {
            mechanicApi.searchMechanics(
                query = params.query,
                city = params.city,
                state = params.state,
                specialty = params.specialty
            )
        }.map { resource ->
            when (resource) {
                is Resource.Success -> {
                    val mechanics = resource.data.map { mechanicDto ->
                        Mechanic(
                            id = mechanicDto.id,
                            name = mechanicDto.name,
                            address = mechanicDto.address,
                            city = mechanicDto.city,
                            state = mechanicDto.state,
                            zipCode = mechanicDto.zipCode,
                            phone = mechanicDto.phone,
                            email = mechanicDto.email,
                            website = mechanicDto.website,
                            specialties = mechanicDto.specialties,
                            operatingHours = mechanicDto.operatingHours,
                            averageRating = mechanicDto.averageRating,
                            totalReviews = mechanicDto.totalReviews,
                            createdAt = mechanicDto.createdAt,
                            updatedAt = mechanicDto.updatedAt
                        )
                    }

                    Resource.Success(mechanics)
                }
                is Resource.Error -> Resource.Error(resource.message, resource.code)
                is Resource.Loading -> Resource.Loading
            }
        }
    }
}