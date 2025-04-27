package com.tamersarioglu.ratemecha.domain.usecase.mechanicusecases

import com.tamersarioglu.ratemecha.domain.model.Mechanic
import com.tamersarioglu.ratemecha.domain.repository.MechanicRepository
import com.tamersarioglu.ratemecha.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMechanicByIdUseCase @Inject constructor(
    private val mechanicRepository: MechanicRepository
) {
    operator fun invoke(id: String): Flow<Resource<Mechanic>> {
        return mechanicRepository.getMechanicById(id)
    }
}