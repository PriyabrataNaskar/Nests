package com.priyo.nests.domain.model

data class Filters(
    var facilities: ArrayList<FacilityType>,
    val exclusions: List<List<ExclusionRules>>,
)
