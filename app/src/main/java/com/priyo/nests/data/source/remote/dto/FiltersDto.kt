package com.priyo.nests.data.source.remote.dto

import com.google.gson.annotations.SerializedName

data class FiltersDto(
    @SerializedName("facilities")
    val facilities: List<FacilityDto>?,
    @SerializedName("exclusions")
    val exclusions: List<List<ExclusionDto>>?,
)
