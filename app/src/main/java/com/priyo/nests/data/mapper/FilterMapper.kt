package com.priyo.nests.data.mapper

import com.priyo.nests.core.extentions.toIntOrZero
import com.priyo.nests.data.source.remote.dto.ExclusionDto
import com.priyo.nests.data.source.remote.dto.FacilityDto
import com.priyo.nests.data.source.remote.dto.FiltersDto
import com.priyo.nests.domain.enums.FacilityOptionEnum
import com.priyo.nests.domain.enums.FacilityTypeEnum
import com.priyo.nests.domain.model.ExclusionRules
import com.priyo.nests.domain.model.FacilityOption
import com.priyo.nests.domain.model.FacilityType
import com.priyo.nests.domain.model.Filters

object FilterMapper {

    fun toFilters(filter: FiltersDto): Filters {
        return Filters(
            facilities = toFacilities(filter.facilities),
            exclusions = toExclusions(filter.exclusions),
        )
    }

    private fun toExclusions(exclusionsDto: List<List<ExclusionDto>>?): List<List<ExclusionRules>> {
        return exclusionsDto?.map { exclusionList ->
            exclusionList.map { exclusionDto ->
                ExclusionRules(
                    exclusionDto.facilityId.toIntOrZero,
                    exclusionDto.optionsId.toIntOrZero,
                )
            }
        } ?: emptyList()
    }

    private fun toFacilities(facilitiesDto: List<FacilityDto?>?): ArrayList<FacilityType> {
        return facilitiesDto?.map { facilityDto ->
            val options = facilityDto?.options?.map { optionDto ->
                FacilityOption(
                    id = optionDto.id.toIntOrZero,
                    name = optionDto.name.orEmpty(),
                    icon = optionDto.icon.orEmpty(),
                    isAvailable = false,
                    isChecked = false,
                    facilityOptionEnum = getFacilityOptionEnum(optionDto.id.toIntOrZero),
                )
            }?.toCollection(ArrayList()) ?: arrayListOf()
            FacilityType(
                id = facilityDto?.facilityId.toIntOrZero,
                name = facilityDto?.name.orEmpty(),
                facilityOptions = options,
                facilityTypeEnum = getFacilityTypeEnum(facilityDto?.facilityId.toIntOrZero),
                isExpanded = false,
            )
        }?.toCollection(ArrayList()) ?: ArrayList()
    }

    private fun getFacilityTypeEnum(facilityId: Int): FacilityTypeEnum {
        when (facilityId) {
            FacilityTypeEnum.Property.facilityId -> return FacilityTypeEnum.Property
            FacilityTypeEnum.Rooms.facilityId -> return FacilityTypeEnum.Rooms
            FacilityTypeEnum.Others.facilityId -> return FacilityTypeEnum.Others
        }
        return FacilityTypeEnum.Invalid
    }

    private fun getFacilityOptionEnum(optionId: Int): FacilityOptionEnum {
        return when (optionId) {
            FacilityOptionEnum.Apartment.optionId -> FacilityOptionEnum.Apartment
            FacilityOptionEnum.Condo.optionId -> FacilityOptionEnum.Condo
            FacilityOptionEnum.Boat.optionId -> FacilityOptionEnum.Boat
            FacilityOptionEnum.Land.optionId -> FacilityOptionEnum.Land
            FacilityOptionEnum.Rooms.optionId -> FacilityOptionEnum.Rooms
            FacilityOptionEnum.NoRooms.optionId -> FacilityOptionEnum.NoRooms
            FacilityOptionEnum.Swimming.optionId -> FacilityOptionEnum.Swimming
            FacilityOptionEnum.Garden.optionId -> FacilityOptionEnum.Garden
            FacilityOptionEnum.Garage.optionId -> FacilityOptionEnum.Garage
            else -> FacilityOptionEnum.Invalid
        }
    }
}
