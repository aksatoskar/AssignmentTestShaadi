package com.tatadigital.tcp.payments.upipayment.paytovpa.data.repository

import com.example.assignment.model.MatchProfileResponse
import com.example.assignment.model.Resource

interface INetworkSource {
    suspend fun fetchProfiles(id: Int): Resource<MatchProfileResponse>
}