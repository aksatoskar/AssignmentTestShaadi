package com.tatadigital.tcp.payments.upipayment.paytovpa.data.repository

import com.example.assignment.model.ProfileDetails
import com.example.assignment.model.Resource
import kotlinx.coroutines.flow.Flow


interface IDataSource {

    suspend fun updateProfile(profileDetails: ProfileDetails): Flow<Resource<Boolean>>

    suspend fun insertProfiles(profilesList: List<ProfileDetails>)

    suspend fun getAllProfiles(): List<ProfileDetails>
}