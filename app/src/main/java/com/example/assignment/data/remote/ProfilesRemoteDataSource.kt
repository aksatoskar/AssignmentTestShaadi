package com.example.assignment.data.remote

import com.example.assignment.model.MatchProfileResponse
import com.example.assignment.network.ApiService
import com.example.assignment.model.Result
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * fetches data from remote source
 */
class ProfilesRemoteDataSource @Inject constructor(private val retrofit: Retrofit) : BaseRepository() {

    suspend fun fetchProfiles(id: Int): Result<MatchProfileResponse> {
        val apiService = retrofit.create(ApiService::class.java);
        return getResponse(
                request = { apiService.getMatchProfiles(id) },
                defaultErrorMessage = "Error fetching profile list",
                retrofit
        )
    }
}