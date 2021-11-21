package com.tatadigital.tcp.payments.upipayment.paytovpa.data.repository

import com.example.assignment.data.local.ProfileDao
import com.example.assignment.data.remote.BaseRepository
import com.example.assignment.model.MatchProfileResponse
import com.example.assignment.model.ProfileDetails
import com.example.assignment.model.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DataSourceImpl @Inject constructor(
    private val profileDao: ProfileDao,
    private var ioDispatcher: CoroutineDispatcher
) : BaseRepository(), IDataSource {
    override suspend fun updateProfile(profileDetails: ProfileDetails) {
        flow {
            emit(Resource.loading())
            profileDao.update(profileDetails)
            emit(Resource.success(null))
        }.flowOn(ioDispatcher)
    }

    override suspend fun insertProfiles(profilesList: List<ProfileDetails>) {
        profileDao.insertAll(profilesList)
    }

    override suspend fun getAllProfiles(): List<ProfileDetails> {
        return profileDao.getAll()
    }

}