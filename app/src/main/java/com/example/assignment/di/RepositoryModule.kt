package com.example.assignment.di

import com.tatadigital.tcp.payments.upipayment.paytovpa.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun provideIOCoroutineDispatcher(): CoroutineDispatcher {
        return ioDispatcher
    }

    @Singleton
    @Provides
    fun provideUpiSourceRepositoryImpl(source: SourceRepositoryImpl): ISourceRepository {
        return source
    }

    @Singleton
    @Provides
    fun provideUpiNetworkSource(networkSourceImpl: NetworkSourceImpl): INetworkSource {
        return networkSourceImpl
    }

    @Singleton
    @Provides
    fun provideUpiDataSource(dataSourceImpl: DataSourceImpl): IDataSource {
        return dataSourceImpl
    }
}