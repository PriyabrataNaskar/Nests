package com.priyo.nests.data.source.remote.dto

import com.google.gson.annotations.SerializedName

data class ExclusionDto(
    @SerializedName("facility_id")
    val facilityId: String?,
    @SerializedName("options_id")
    val optionsId: String?,
)
