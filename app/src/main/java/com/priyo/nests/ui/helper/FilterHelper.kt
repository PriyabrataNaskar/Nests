package com.priyo.nests.ui.helper

import com.priyo.nests.core.mvicore.IEffect
import com.priyo.nests.core.mvicore.IIntent
import com.priyo.nests.core.mvicore.IState
import com.priyo.nests.domain.model.FacilityOption
import com.priyo.nests.domain.model.FacilityType

sealed class FilterEffect : IEffect {
    data class UpdateFacilityOption(val filters: List<FacilityType>, val parentPosition: Int) : FilterEffect()
}

sealed class FilterState : IState {
    object Idle : FilterState()
    object Loading : FilterState()
    data class Error(val message: String) : FilterState()
    data class Init(val filters: List<FacilityType>) : FilterState()
    data class UpdateFacilityTypeState(val filters: List<FacilityType>) : FilterState()
}

sealed class FilterIntent : IIntent {
    object Init : FilterIntent()
    data class FacilityTypeCta(val facilityType: FacilityType, val position: Int) : FilterIntent()
    data class FacilityOptionCta(val facilityOption: FacilityOption, val parentPosition: Int, val childPosition: Int) : FilterIntent()
}
