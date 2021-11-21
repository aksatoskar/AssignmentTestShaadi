package com.example.assignment.network

import com.example.assignment.model.MatchProfileResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/api/")
    suspend fun getMatchProfiles(@Query("results") results: Int) : MatchProfileResponse
}