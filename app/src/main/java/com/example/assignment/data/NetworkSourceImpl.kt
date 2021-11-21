package com.tatadigital.tcp.payments.upipayment.paytovpa.data.repository

import com.example.assignment.data.remote.BaseRepository
import com.example.assignment.model.MatchProfileResponse
import com.example.assignment.model.Resource
import com.example.assignment.network.ApiService
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class NetworkSourceImpl @Inject constructor(
    private var dispatcher: CoroutineDispatcher,
    private val apiService: ApiService
) : BaseRepository(), INetworkSource {
    override suspend fun fetchProfiles(id: Int): Resource<MatchProfileResponse> {
        return safeApiCall(dispatcher) {
            apiService.getMatchProfiles(id)
        }
    }
}