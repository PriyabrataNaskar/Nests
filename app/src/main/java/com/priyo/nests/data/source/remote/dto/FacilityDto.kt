package com.priyo.nests.data.source.remote.dto

import com.google.gson.annotations.SerializedName

data class FacilityDto(
    @SerializedName("facility_id")
    val facilityId: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("options")
    val options: List<OptionDto>?,
)
