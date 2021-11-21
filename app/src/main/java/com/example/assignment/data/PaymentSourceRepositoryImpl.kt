package com.tatadigital.tcp.payments.upipayment.paytovpa.data.repository

import android.content.Context
import com.example.assignment.R
import com.example.assignment.model.MatchProfileResponse
import com.example.assignment.model.ProfileDetails
import com.example.assignment.model.Resource
import com.example.assignment.util.isNetworkAvailable
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PaymentSourceRepositoryImpl @Inject constructor(
    private var dataSource: IDataSource,
    private var networkSource: INetworkSource,
    private var dispatcher: CoroutineDispatcher,
    @ApplicationContext private var context: Context
) : ISourceRepository {
    override suspend fun updateProfile(profileDetails: ProfileDetails): Flow<Resource<Boolean>> {
        return dataSource.updateProfile(profileDetails)
    }

    override suspend fun insertProfiles(profilesList: List<ProfileDetails>) {
        return dataSource.insertProfiles(profilesList)
    }

    override suspend fun getAllProfiles(): List<ProfileDetails> {
        return dataSource.getAllProfiles()
    }

    override suspend fun fetchProfiles(id: Int): Flow<Resource<MatchProfileResponse>> {
        return flow {
            emit(Resource.loading())
            if(!context.isNetworkAvailable()) {
                emit(Resource.error(Exception(context.getString(R.string.failed_network_msg)), MatchProfileResponse(getAllProfiles())))
            } else {
                val result: Resource<MatchProfileResponse> = networkSource.fetchProfiles(id)

                //Cache to database if response is successful
                if (result.status == Resource.Status.SUCCESS) {
                    result.data?.results?.let { it ->
                        dataSource.insertProfiles(it)
                        emit(Resource.success(MatchProfileResponse(dataSource.getAllProfiles())))
                    }
                } else {
                    emit(Resource.error(result.throwable, MatchProfileResponse(getAllProfiles())))
                }
            }
        }.flowOn(dispatcher)
    }
}