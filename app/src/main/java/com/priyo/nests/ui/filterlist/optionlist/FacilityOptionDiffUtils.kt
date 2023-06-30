package com.priyo.nests.ui.filterlist.optionlist

import androidx.recyclerview.widget.DiffUtil
import com.priyo.nests.domain.model.FacilityOption

class FacilityOptionDiffUtils(
    private val oldList: List<FacilityOption>,
    private val newList: List<FacilityOption>,
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
            oldList.get(oldItemPosition).icon != newList.get(
                newItemPosition,
            ).icon -> {
                false
            }
            oldList.get(oldItemPosition).name != newList.get(
                newItemPosition,
            ).name -> {
                false
            }
            oldList.get(oldItemPosition).isAvailable != newList.get(
                newItemPosition,
            ).isAvailable -> {
                false
            }
            oldList.get(oldItemPosition).isChecked != newList.get(
                newItemPosition,
            ).isChecked -> {
                false
            }
            else -> true
        }
    }
}
