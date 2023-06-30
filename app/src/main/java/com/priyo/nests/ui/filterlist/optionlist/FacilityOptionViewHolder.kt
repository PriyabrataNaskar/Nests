package com.priyo.nests.ui.filterlist.optionlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.priyo.nests.R
import com.priyo.nests.databinding.ItemFacilityOptionBinding
import com.priyo.nests.domain.enums.FacilityOptionEnum
import com.priyo.nests.domain.model.FacilityOption
import com.priyo.nests.extentions.setDrawableStart

class FacilityOptionViewHolder(
    private val binding: ItemFacilityOptionBinding,
    private val onItemSelect: ((FacilityOption, Int) -> Unit)?,
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(
            viewGroup: ViewGroup,
            onItemSelect: ((FacilityOption, Int) -> Unit)?,
        ): FacilityOptionViewHolder {
            return FacilityOptionViewHolder(
                ItemFacilityOptionBinding.inflate(
                    LayoutInflater.from(
                        viewGroup.context,
                    ),
                    viewGroup,
                    false,
                ),
                onItemSelect,
            )
        }
    }

    fun bindData(
        baseItem: FacilityOption,
        position: Int,
    ) {
        setViewData(baseItem, position)
        setOnClickListener(baseItem, position)
    }

    private fun setViewData(option: FacilityOption, position: Int) {
        with(binding) {
            tvFacilityOption.apply {
                text = option.name
                setDrawableStart(getFacilityOptionDrawable(option.facilityOptionEnum))
            }
            rbSelection.isChecked = option.isChecked
        }
    }

    private fun getFacilityOptionDrawable(facilityOptionEnum: FacilityOptionEnum): Int {
        return when (facilityOptionEnum) {
            FacilityOptionEnum.Apartment -> R.drawable.apartment
            FacilityOptionEnum.Condo -> R.drawable.condo
            FacilityOptionEnum.Boat -> R.drawable.boat
            FacilityOptionEnum.Land -> R.drawable.land
            FacilityOptionEnum.Rooms -> R.drawable.rooms
            FacilityOptionEnum.NoRooms -> R.drawable.no_room
            FacilityOptionEnum.Swimming -> R.drawable.swimming
            FacilityOptionEnum.Garden -> R.drawable.garden
            FacilityOptionEnum.Garage -> R.drawable.garage
            else -> android.R.drawable.stat_notify_error
        }
    }

    private fun setOnClickListener(option: FacilityOption, position: Int) {
        with(binding) {
            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.rb_selection -> {
                        onItemSelect?.invoke(
                            option,
                            position,
                        )
                    }
                }
            }
        }
    }
}
