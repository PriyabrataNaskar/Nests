package com.priyo.nests.data.source.local.datasource

import com.priyo.nests.corenetwork.NetworkResult
import com.priyo.nests.domain.model.Filters

interface IFilterLocalDataSource {
    suspend fun getFilters(): NetworkResult<Filters>
    suspend fun updateFilters(filters: Filters): NetworkResult<Any>
    suspend fun deleteAllFilters(): NetworkResult<Any>
}
