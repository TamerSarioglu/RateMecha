package com.tamersarioglu.ratemecha.util

import com.google.gson.Gson
import com.tamersarioglu.ratemecha.data.remote.model.ErrorResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException

inline fun <T> safeApiCall(crossinline apiCall: suspend () -> Response<T>): Flow<Resource<T>> = flow {
    emit(Resource.Loading)

    try {
        val response = apiCall()
        if (response.isSuccessful) {
            response.body()?.let {
                emit(Resource.Success(it))
            } ?: emit(Resource.Error("Response body is null"))
        } else {
            val errorBody = response.errorBody()?.string()
            val errorMessage = try {
                val errorResponse = Gson().fromJson(errorBody, ErrorResponseDto::class.java)
                errorResponse.message
            } catch (e: Exception) {
                "Unknown error occurred"
            }
            emit(Resource.Error(errorMessage, response.code()))
        }
    } catch (e: IOException) {
        emit(Resource.Error("Couldn't reach server. Check your internet connection."))
    } catch (e: Exception) {
        emit(Resource.Error(e.message ?: "An unexpected error occurred"))
    }
}