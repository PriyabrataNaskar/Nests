package com.priyo.nests.data.source.remote.service

import com.priyo.nests.data.source.remote.dto.FiltersDto
import retrofit2.Response
import retrofit2.http.GET

interface FilterServices {

    @GET(FILTER_PATH)
    suspend fun getFilters(): Response<FiltersDto>
	
    companion object {
        const val FILTER_PATH = "iranjith4/ad-assignment/db"
    }
}
