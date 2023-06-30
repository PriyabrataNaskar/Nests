package com.priyo.nests.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priyo.nests.core.mvicore.IModel
import com.priyo.nests.core.result.UiResult
import com.priyo.nests.corenetwork.ERROR.SOMETHING_WENT_WRONG
import com.priyo.nests.domain.model.FacilityOption
import com.priyo.nests.domain.model.FacilityType
import com.priyo.nests.domain.model.Filters
import com.priyo.nests.domain.usecase.FetchFiltersUseCase
import com.priyo.nests.ui.helper.FilterEffect
import com.priyo.nests.ui.helper.FilterIntent
import com.priyo.nests.ui.helper.FilterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val fetchFiltersUseCase: FetchFiltersUseCase,
) : ViewModel(), IModel<FilterState, FilterIntent, FilterEffect> {

    override val intents: Channel<FilterIntent> =
        Channel(Channel.UNLIMITED)

    private val _uiState =
        MutableStateFlow<FilterState>(FilterState.Idle)
    override val uiState: StateFlow<FilterState>
        get() = _uiState

    private val _uiEffect = Channel<FilterEffect>()
    override val uiEffect: Flow<FilterEffect>
        get() = _uiEffect.receiveAsFlow()

    private var filters: Filters? = null

    init {
        handleIntent()
    }

    override fun handleIntent() {
        viewModelScope.launch {
            intents.consumeAsFlow().collect {
                when (it) {
                    FilterIntent.Init -> {
                        getFilters()
                    }
                    is FilterIntent.FacilityOptionCta -> {
                        updateFacilityOption(it.facilityOption, childPosition = it.childPosition, parentPosition = it.parentPosition)
                    }
                    is FilterIntent.FacilityTypeCta -> {
                        showOrHideFacilityType(it.facilityType, it.position)
                    }
                }
            }
        }
    }

    private fun getFilters() {
        viewModelScope.launch {
            _uiState.emit(FilterState.Loading)
            fetchFiltersUseCase.invoke().collect {
                when (it) {
                    is UiResult.Error -> {
                        _uiState.emit(
                            FilterState.Error(
                                it.error.message ?: SOMETHING_WENT_WRONG,
                            ),
                        )
                    }

                    is UiResult.Success -> {
                        filters = it.data
                        _uiState.emit(
                            FilterState.Init(
                                it.data.facilities.map { it2 ->
                                    it2.copy()
                                },
                            ),
                        )
                    }
                }
            }
            _uiState.emit(FilterState.Idle)
        }
    }

    private suspend fun showOrHideFacilityType(
        facilityType: FacilityType,
        position: Int,
    ) {
        _uiState.emit(FilterState.Idle)
        val isExpanded = facilityType.isExpanded
        filters?.let {
            it.facilities[position].isExpanded = isExpanded.not()
            val facilityTypes = it.facilities.map { types -> types.copy() }
            _uiState.emit(FilterState.UpdateFacilityTypeState(facilityTypes))
        }
        Log.e("showOrHideFacilityType", filters.toString()) // todo: remove log
    }

    private suspend fun updateFacilityOption(
        facilityOption: FacilityOption,
        parentPosition: Int,
        childPosition: Int,
    ) {
        _uiState.emit(FilterState.Idle)
        filters?.let {
            val facilityTypes = it.facilities.map { types -> types.copy() }
//            facilityTypes.onEach { type ->
//                type.facilityOptions = type.facilityOptions.map { facilityOption -> facilityOption.copy() }.toCollection(ArrayList())
//            }
            facilityTypes[parentPosition].facilityOptions.onEach {
                it.isChecked = false
            }
            facilityTypes[parentPosition].facilityOptions[childPosition].isChecked = true
            _uiEffect.send(FilterEffect.UpdateFacilityOption(facilityTypes, parentPosition))
        }
//        filters?.facilities?.get(parentPosition)?.facilityOptions?.onEach {
//            it.isChecked = false
//        }
//        filters?.facilities?.get(parentPosition)?.facilityOptions?.get(childPosition)?.isChecked = true
//        Log.e("updateFacilityOption", filters.toString())
        _uiState.emit(FilterState.Idle)
    }
}
