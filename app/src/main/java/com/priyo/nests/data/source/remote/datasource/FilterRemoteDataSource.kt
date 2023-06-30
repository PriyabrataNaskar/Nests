package com.priyo.nests.data.source.remote.datasource

import com.google.gson.Gson
import com.priyo.nests.corenetwork.ERROR.SOMETHING_WENT_WRONG
import com.priyo.nests.corenetwork.NetworkResult
import com.priyo.nests.corenetwork.safeApiCall
import com.priyo.nests.data.mapper.FilterMapper
import com.priyo.nests.data.source.remote.dto.FiltersDto
import com.priyo.nests.data.source.remote.service.FilterServices
import com.priyo.nests.domain.model.Filters
import retrofit2.Response
import javax.inject.Inject

class FilterRemoteDataSource @Inject constructor(
    private val apiServices: FilterServices,
) : IFilterRemoteDataSource {

    override suspend fun getFilters(): NetworkResult<Filters> {
        safeApiCall {
            apiServices.getFilters()
        }.onSuccess {
            return NetworkResult.Success(FilterMapper.toFilters(it))
        }.onFailure {
            return NetworkResult.Error(
                throwable = it,
            )
        }
        return NetworkResult.Error(
            throwable = Exception(
                SOMETHING_WENT_WRONG,
            ),
        )
    }
}

