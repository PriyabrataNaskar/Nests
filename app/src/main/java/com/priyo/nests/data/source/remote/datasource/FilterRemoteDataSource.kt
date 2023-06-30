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
            // apiServices.getFilters()
            Response.success(FakeData.getData()) // todo: remove
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

class FakeData() {
    companion object {
        private fun parseFiltersJson(json: String): FiltersDto {
            val gson = Gson()
            return gson.fromJson(json, FiltersDto::class.java)
        }

        fun getData(): FiltersDto {
            val jsonResponse = "{\n" +
                "  \"facilities\": [\n" +
                "    {\n" +
                "      \"facility_id\": \"1\",\n" +
                "      \"name\": \"Property Type\",\n" +
                "      \"options\": [\n" +
                "        {\n" +
                "          \"name\": \"Apartment\",\n" +
                "          \"icon\": \"apartment\",\n" +
                "          \"id\": \"1\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"Condo\",\n" +
                "          \"icon\": \"condo\",\n" +
                "          \"id\": \"2\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"Boat House\",\n" +
                "          \"icon\": \"boat\",\n" +
                "          \"id\": \"3\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"Land\",\n" +
                "          \"icon\": \"land\",\n" +
                "          \"id\": \"4\"\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"facility_id\": \"2\",\n" +
                "      \"name\": \"Number of Rooms\",\n" +
                "      \"options\": [\n" +
                "        {\n" +
                "          \"name\": \"1 to 3 Rooms\",\n" +
                "          \"icon\": \"rooms\",\n" +
                "          \"id\": \"6\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"No Rooms\",\n" +
                "          \"icon\": \"no-room\",\n" +
                "          \"id\": \"7\"\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"facility_id\": \"3\",\n" +
                "      \"name\": \"Other facilities\",\n" +
                "      \"options\": [\n" +
                "        {\n" +
                "          \"name\": \"Swimming Pool\",\n" +
                "          \"icon\": \"swimming\",\n" +
                "          \"id\": \"10\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"Garden Area\",\n" +
                "          \"icon\": \"garden\",\n" +
                "          \"id\": \"11\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"Garage\",\n" +
                "          \"icon\": \"garage\",\n" +
                "          \"id\": \"12\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"exclusions\": [\n" +
                "    [\n" +
                "      {\n" +
                "        \"facility_id\": \"1\",\n" +
                "        \"options_id\": \"4\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"facility_id\": \"2\",\n" +
                "        \"options_id\": \"6\"\n" +
                "      }\n" +
                "    ],\n" +
                "    [\n" +
                "      {\n" +
                "        \"facility_id\": \"1\",\n" +
                "        \"options_id\": \"3\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"facility_id\": \"3\",\n" +
                "        \"options_id\": \"12\"\n" +
                "      }\n" +
                "    ],\n" +
                "    [\n" +
                "      {\n" +
                "        \"facility_id\": \"2\",\n" +
                "        \"options_id\": \"7\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"facility_id\": \"3\",\n" +
                "        \"options_id\": \"12\"\n" +
                "      }\n" +
                "    ]\n" +
                "  ]\n" +
                "}"
            return parseFiltersJson(jsonResponse)
        }
    }
}
