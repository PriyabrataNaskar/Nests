package com.priyo.nests.domain.model

import com.priyo.nests.domain.enums.FacilityOptionEnum

data class FacilityOption(
    val id: Int,
    val name: String,
    val icon: String,
    var isAvailable: Boolean,
    var isChecked: Boolean,
    val facilityOptionEnum: FacilityOptionEnum,
)
