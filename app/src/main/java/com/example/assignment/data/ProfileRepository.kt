package com.example.assignment.data

import com.example.assignment.model.MatchProfileResponse
import com.example.assignment.data.remote.ProfilesRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import com.example.assignment.model.Result
import com.example.assignment.data.local.ProfileDao
import javax.inject.Inject

/**
 * Repository which fetches data from Remote or Local data sources
 */
class ProfileRepository @Inject constructor(
    private val profilesRemoteDataSource: ProfilesRemoteDataSource,
    private val profileDao: ProfileDao
) {

    suspend fun fetchProfiles(id: Int): Flow<Result<MatchProfileResponse>> {
        return flow {
            emit(Result.loading())
            emit(profilesRemoteDataSource.fetchProfiles(id))
        }.flowOn(Dispatchers.IO)
    }
}