package com.example.assignment.data

import com.example.assignment.model.MatchProfileResponse
import com.example.assignment.data.remote.ProfilesRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import com.example.assignment.model.Result
import com.example.assignment.data.local.ProfileDao
import com.example.assignment.model.ProfileDetails
import javax.inject.Inject

/**
 * Repository which fetches data from Remote or Local data sources
 */
class ProfileRepository @Inject constructor(
    private val profilesRemoteDataSource: ProfilesRemoteDataSource,
    private val profileDao: ProfileDao
) {

    suspend fun fetchProfiles(id: Int): Flow<Result<MatchProfileResponse>?> {
        return flow {
            emit(Result.loading())
            val result = profilesRemoteDataSource.fetchProfiles(id)

            //Cache to database if response is successful
            if (result.status == Result.Status.SUCCESS) {
                result.data?.results?.let { it ->
                    profileDao.insertAll(it)
                    emit(Result.success(MatchProfileResponse(profileDao.getAll())))
                }
            } else {
                emit(fetchCachedProfiles())
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun fetchCachedProfiles(): Result<MatchProfileResponse>? =
        profileDao.getAll()?.let {
            Result.success(MatchProfileResponse(it))
        }

    suspend fun updateProfile(profileDetails: ProfileDetails): Flow<Result<Boolean>> {
        return flow {
            emit(Result.loading())
            val result = profileDao.update(profileDetails)
            emit(Result.success(true))
        }.flowOn(Dispatchers.IO)
    }
}
