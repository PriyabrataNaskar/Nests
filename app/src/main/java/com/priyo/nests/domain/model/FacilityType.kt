package com.priyo.nests.domain.model

import com.priyo.nests.domain.enums.FacilityTypeEnum

data class FacilityType(
    val id: Int,
    val name: String,
    var facilityOptions: ArrayList<FacilityOption>,
    val facilityTypeEnum: FacilityTypeEnum,
    var isExpanded: Boolean = false,
)
