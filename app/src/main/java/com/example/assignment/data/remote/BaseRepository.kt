package com.example.assignment.data.remote

import com.example.assignment.model.Result
import io.buildwithnd.demotmdb.util.ErrorUtils
import retrofit2.Response
import retrofit2.Retrofit

abstract class BaseRepository {

    suspend fun <T> getResponse(request: suspend () -> Response<T>, defaultErrorMessage: String, retrofit: Retrofit): Result<T> {
        return try {
            println("I'm working in thread ${Thread.currentThread().name}")
            val result = request.invoke()
            if (result.isSuccessful) {
                return Result.success(result.body())
            } else {
                val errorResponse = ErrorUtils.parseError(result, retrofit)
                Result.error(errorResponse?.status_message ?: defaultErrorMessage, errorResponse)
            }
        } catch (e: Throwable) {
            Result.error("Unknown Error", null)
        }
    }
}