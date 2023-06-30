package com.priyo.nests.ui.filterlist

import androidx.recyclerview.widget.DiffUtil
import com.priyo.nests.domain.model.FacilityType

class FacilityDiffUtils(
    private val oldList: List<FacilityType>,
    private val newList: List<FacilityType>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
    ): Boolean {
        return oldList.get(oldItemPosition).id == newList.get(
            newItemPosition,
        ).id
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
    ): Boolean {
        return when {
            oldList.get(oldItemPosition).id != newList.get(
                newItemPosition,
            ).id -> {
                false
            }
            oldList.get(oldItemPosition).name != newList.get(
                newItemPosition,
            ).name -> {
                false
            }
            oldList.get(oldItemPosition).isExpanded != newList.get(
                newItemPosition,
            ).isExpanded -> {
                false
            }
            oldList.get(oldItemPosition).facilityOptions != newList.get(
                newItemPosition,
            ).facilityOptions -> {
                false
            }
            !equalsIgnoreOrder(oldList.get(oldItemPosition).facilityOptions, newList.get(newItemPosition).facilityOptions) -> {
                false
            }
            else -> true
        }
    }
    private fun <T> equalsIgnoreOrder(list1: List<T>, list2: List<T>) = list1.size == list2.size && list1.toSet() == list2.toSet()
}
