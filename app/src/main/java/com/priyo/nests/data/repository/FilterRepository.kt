package com.priyo.nests.data.repository

import com.priyo.nests.corenetwork.NetworkResult
import com.priyo.nests.data.source.remote.datasource.IFilterRemoteDataSource
import com.priyo.nests.domain.model.Filters
import com.priyo.nests.domain.repository.IFilterRepository
import javax.inject.Inject

class FilterRepository @Inject constructor(
    private val filterRemoteDataSource: IFilterRemoteDataSource,
) : IFilterRepository {

    override suspend fun getFilters(): NetworkResult<Filters> {
        return filterRemoteDataSource.getFilters()
    }
}
