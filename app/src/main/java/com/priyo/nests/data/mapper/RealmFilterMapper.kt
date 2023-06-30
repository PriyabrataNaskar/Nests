package com.priyo.nests.data.mapper

import com.priyo.nests.core.extentions.toIntOrZero
import com.priyo.nests.data.source.local.dao.ExclusionDao
import com.priyo.nests.data.source.local.dao.ExclusionSetDao
import com.priyo.nests.data.source.local.dao.FacilityDao
import com.priyo.nests.data.source.local.dao.FiltersDao
import com.priyo.nests.data.source.local.dao.OptionDao
import com.priyo.nests.domain.enums.FacilityOptionEnum
import com.priyo.nests.domain.enums.FacilityTypeEnum
import com.priyo.nests.domain.model.ExclusionRules
import com.priyo.nests.domain.model.FacilityOption
import com.priyo.nests.domain.model.FacilityType
import com.priyo.nests.domain.model.Filters
import io.realm.RealmList

object RealmFilterMapper {

    fun toFilters(filter: FiltersDao): Filters {
        return Filters(
            facilities = toFacilities(filter.facilities),
            exclusions = toExclusions(filter.exclusions),
        )
    }

    private fun toExclusions(exclusionsDao: RealmList<ExclusionSetDao>): List<List<ExclusionRules>> {
        return exclusionsDao.map { exclusionList ->
            exclusionList.exclusionSet.map { exclusionDao ->
                ExclusionRules(
                    exclusionDao.facilityId.toIntOrZero,
                    exclusionDao.optionsId.toIntOrZero,
                )
            }
        }
    }

    private fun toFacilities(facilitiesDao: List<FacilityDao>): ArrayList<FacilityType> {
        val facilityTypes: ArrayList<FacilityType> = arrayListOf()
        facilitiesDao.forEach { facilityDto ->
            val options = facilityDto?.options?.map { optionDto ->
                FacilityOption(
                    id = optionDto?.id.toIntOrZero,
                    name = optionDto?.name.orEmpty(),
                    icon = optionDto?.icon.orEmpty(),
                    isAvailable = false,
                    isChecked = false,
                    facilityOptionEnum = getFacilityOptionEnum(optionDto?.id.toIntOrZero),
                )
            }?.toCollection(ArrayList()) ?: arrayListOf()
            facilityTypes.add(
                FacilityType(
                    id = facilityDto.facilityId.toIntOrZero,
                    name = facilityDto.name.orEmpty(),
                    facilityOptions = options,
                    facilityTypeEnum = getFacilityTypeEnum(facilityDto?.facilityId.toIntOrZero),
                    isExpanded = false,
                ),
            )
        }
        return facilityTypes
    }

    private fun toFacilitiesDao(facilities: List<FacilityType>?): RealmList<FacilityDao> {
        return facilities?.map { facilityDto ->
            val options = toOptionDaoList(facilityDto.facilityOptions)
            FacilityDao().apply {
                this.facilityId = facilityDto?.id.toString().orEmpty()
                this.name = facilityDto?.name.orEmpty()
                this.options = options
            }
        }?.toCollection(RealmList()) ?: RealmList()
    }

    private fun toOptionDaoList(options: ArrayList<FacilityOption>): RealmList<OptionDao> {
        val optionDaoList: RealmList<OptionDao> = RealmList()
        options.forEach {
            optionDaoList.add(toOptionDao(it))
        }
        return optionDaoList
    }

    private fun toOptionDao(option: FacilityOption): OptionDao {
        val optionDao = OptionDao().apply {
            id = option.id?.toString().orEmpty()
            name = option?.name.orEmpty()
            icon = option?.icon.orEmpty()
        }
        return optionDao
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

    private fun toExclusionListDao(exclusions: List<List<ExclusionRules>>?): RealmList<ExclusionSetDao> {
        val exclusionListDao = RealmList<ExclusionSetDao>()
        exclusions?.forEach {
            val exclusionSetDao = ExclusionSetDao()
            it.forEach {
                exclusionSetDao.exclusionSet.add(toExclusionDao(it))
            }
            exclusionListDao.add(exclusionSetDao)
        }
        return exclusionListDao
    }

    private fun toExclusionDao(exclusion: ExclusionRules): ExclusionDao {
        val exclusionDao = ExclusionDao().apply {
            this.facilityId = exclusion.facilityId.toString()
            this.optionsId = exclusion.optionsId.toString()
        }
        return exclusionDao
    }

    fun toFiltersDao(filter: Filters): FiltersDao {
        return FiltersDao().apply {
            this.facilities = toFacilitiesDao(filter.facilities)
            this.exclusions = toExclusionListDao(filter.exclusions)
        }
    }
}
