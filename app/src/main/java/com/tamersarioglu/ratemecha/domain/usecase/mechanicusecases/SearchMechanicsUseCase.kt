package com.tamersarioglu.ratemecha.domain.usecase.mechanicusecases

import com.tamersarioglu.ratemecha.domain.model.Mechanic
import com.tamersarioglu.ratemecha.domain.model.SearchMechanicParams
import com.tamersarioglu.ratemecha.domain.repository.MechanicRepository
import com.tamersarioglu.ratemecha.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMechanicsUseCase @Inject constructor(
    private val mechanicRepository: MechanicRepository
) {
    operator fun invoke(
        query: String? = null,
        city: String? = null,
        state: String? = null,
        specialty: String? = null
    ): Flow<Resource<List<Mechanic>>> {
        return mechanicRepository.searchMechanics(
            SearchMechanicParams(
                query = query,
                city = city,
                state = state,
                specialty = specialty
            )
        )
    }
}