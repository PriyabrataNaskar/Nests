package com.priyo.nests.data.di

import com.priyo.nests.data.source.local.datasource.FilterLocalDataSource
import com.priyo.nests.data.source.local.datasource.IFilterLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    abstract fun bindFilterDataSourceProviders(
        localDataSource: FilterLocalDataSource,
    ): IFilterLocalDataSource
}
