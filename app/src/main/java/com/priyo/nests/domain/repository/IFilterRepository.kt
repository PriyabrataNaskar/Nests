package com.priyo.nests.domain.repository

import com.priyo.nests.corenetwork.NetworkResult
import com.priyo.nests.domain.model.Filters

interface IFilterRepository {
    suspend fun getFilters(): NetworkResult<Filters>
}
