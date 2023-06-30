package com.priyo.nests.data.di

import com.priyo.nests.data.source.remote.datasource.FilterRemoteDataSource
import com.priyo.nests.data.source.remote.datasource.IFilterRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    abstract fun bindFilterDataSourceProviders(
        remoteDataSource: FilterRemoteDataSource,
    ): IFilterRemoteDataSource
}
