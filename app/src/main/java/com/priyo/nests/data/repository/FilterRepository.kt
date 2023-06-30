package com.priyo.nests.data.repository

import com.priyo.nests.corenetwork.NetworkResult
import com.priyo.nests.data.source.local.datasource.IFilterLocalDataSource
import com.priyo.nests.data.source.remote.datasource.IFilterRemoteDataSource
import com.priyo.nests.domain.model.Filters
import com.priyo.nests.domain.repository.IFilterRepository
import javax.inject.Inject

class FilterRepository @Inject constructor(
    private val filterRemoteDataSource: IFilterRemoteDataSource,
    private val filterLocalDataSource: IFilterLocalDataSource,
) : IFilterRepository {

    override suspend fun getFilters(): NetworkResult<Filters> {
        when (val result = filterLocalDataSource.getFilters()) {
            is NetworkResult.Success -> {
                return if (result.data.facilities.isNotEmpty()) {
                    result
                } else {
                    getFiltersFromRemote()
                }
            }

            is NetworkResult.Error -> {
                return getFiltersFromRemote()
            }
        }
    }

    private suspend fun getFiltersFromRemote(): NetworkResult<Filters> {
        val remoteResult = filterRemoteDataSource.getFilters()
        saveFiltersFromRemote(remoteResult)
        return remoteResult
    }

    private suspend fun saveFiltersFromRemote(remoteResult: NetworkResult<Filters>) {
        when (remoteResult) {
            is NetworkResult.Success -> {
                filterLocalDataSource.updateFilters(remoteResult.data)
            }
            else -> {}
        }
    }
}
