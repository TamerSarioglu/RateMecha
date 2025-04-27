package com.tamersarioglu.ratemecha.data.remote.api

import com.tamersarioglu.ratemecha.data.remote.model.MechanicDto
import com.tamersarioglu.ratemecha.data.remote.model.MechanicWithRatingDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MechanicApi {
    @POST("mechanics")
    suspend fun createMechanic(@Body mechanic: MechanicDto): Response<MechanicDto>

    @GET("mechanics/{id}")
    suspend fun getMechanicById(@Path("id") id: String): Response<MechanicWithRatingDto>

    @GET("mechanics")
    suspend fun searchMechanics(
        @Query("query") query: String? = null,
        @Query("city") city: String? = null,
        @Query("state") state: String? = null,
        @Query("specialty") specialty: String? = null
    ): Response<List<MechanicWithRatingDto>>
}