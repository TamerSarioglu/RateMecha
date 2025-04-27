package com.tamersarioglu.ratemecha.domain.repository

import com.tamersarioglu.ratemecha.domain.model.Mechanic
import com.tamersarioglu.ratemecha.domain.model.SearchMechanicParams
import com.tamersarioglu.ratemecha.util.Resource
import kotlinx.coroutines.flow.Flow

interface MechanicRepository {
    fun getMechanicById(id: String): Flow<Resource<Mechanic>>
    fun searchMechanics(params: SearchMechanicParams): Flow<Resource<List<Mechanic>>>
}