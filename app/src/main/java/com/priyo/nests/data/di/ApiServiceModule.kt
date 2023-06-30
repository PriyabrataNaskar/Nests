package com.priyo.nests.data.di

import com.priyo.nests.data.source.remote.service.FilterServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {
	
    @Provides
    @Singleton
    fun provideFilterServices(retrofit: Retrofit): FilterServices {
        return retrofit.create(FilterServices::class.java)
    }
}
