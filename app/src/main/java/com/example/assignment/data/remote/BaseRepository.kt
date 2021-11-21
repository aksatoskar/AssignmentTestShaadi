package com.example.assignment.data.remote

import com.example.assignment.model.Resource
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException


abstract class BaseRepository {

    suspend inline fun <reified T> safeApiCall(
        dispatcher: CoroutineDispatcher,
        noinline apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(dispatcher) {
            try {
                Resource.success(apiCall.invoke())
            } catch (throwable: Throwable) {
                processForError(throwable,  false, dispatcher)
            }
        }
    }

    suspend inline fun <reified T> processForError(
        throwable: Throwable,
        readErrorBody: Boolean,
        dispatcher: CoroutineDispatcher
    ): Resource<T> {
        return when (throwable) {
            is HttpException -> {
                if (readErrorBody) {
                    val error: T? = convertErrorBody(throwable, dispatcher)
                    Resource.error(throwable, error)
                } else {
                    Resource.error(throwable, null)
                }
            }
            else -> Resource.error(throwable, null)
        }
    }

    suspend inline fun <reified T> convertErrorBody(
        throwable: HttpException,
        dispatcher: CoroutineDispatcher
    ): T? {
        return withContext(dispatcher){
            try {
                throwable.response()?.errorBody()?.source()?.let {
                    val moshi = Moshi.Builder().build()
                    val jsonAdapter: JsonAdapter<T> = moshi.adapter<T>(T::class.java)
                    val data = jsonAdapter.fromJson(it)
                    return@withContext data
                }
            } catch (exception: Exception) {
                null
            }
        }
    }
}