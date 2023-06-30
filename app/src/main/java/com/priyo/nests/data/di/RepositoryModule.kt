package com.priyo.nests.data.di

import com.priyo.nests.data.repository.FilterRepository
import com.priyo.nests.domain.repository.IFilterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindFilterRepositoryProviders(
        filterRepository: FilterRepository,
    ): IFilterRepository
}
