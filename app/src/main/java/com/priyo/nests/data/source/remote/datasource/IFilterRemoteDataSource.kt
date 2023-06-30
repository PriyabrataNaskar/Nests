package com.priyo.nests.data.source.remote.datasource

import com.priyo.nests.corenetwork.NetworkResult
import com.priyo.nests.domain.model.Filters

interface IFilterRemoteDataSource {
    suspend fun getFilters(): NetworkResult<Filters>
}
