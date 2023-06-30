package com.priyo.nests.ui.filterlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.priyo.nests.R
import com.priyo.nests.databinding.ItemFacilityBinding
import com.priyo.nests.domain.enums.FacilityTypeEnum
import com.priyo.nests.domain.model.FacilityOption
import com.priyo.nests.domain.model.FacilityType
import com.priyo.nests.extentions.setDrawableStartAndEnd
import com.priyo.nests.ui.filterlist.optionlist.FacilityOptionAdapter

class FacilityViewHolder(
    private val binding: ItemFacilityBinding,
    private val onItemClick: ((FacilityType, Int) -> Unit)?,
    private val onItemChecked: ((FacilityOption, Int, Int) -> Unit)?,
) : RecyclerView.ViewHolder(binding.root) {

    var childAdapter: FacilityOptionAdapter? = null

    companion object {
        fun create(
            viewGroup: ViewGroup,
            onItemClick: ((FacilityType, Int) -> Unit)?,
            onItemChecked: ((FacilityOption, Int, Int) -> Unit)?,
        ): FacilityViewHolder {
            return FacilityViewHolder(
                ItemFacilityBinding.inflate(
                    LayoutInflater.from(
                        viewGroup.context,
                    ),
                    viewGroup,
                    false,
                ),
                onItemClick,
                onItemChecked,
            )
        }
    }

    fun bindData(
        baseItem: FacilityType,
        position: Int,
    ) {
        setViewData(baseItem, position)
        setOnClickListener(baseItem, position)
    }

    private fun setViewData(facilityItem: FacilityType, position: Int) {
        with(binding) {
            layoutFacilityType.apply {
                tvFacilityType.text = facilityItem.name
                tvFacilityType.setDrawableStartAndEnd(
                    startDrawableResId = getFacilityTypeDrawable(facilityItem.facilityTypeEnum),
                    endDrawableResourceId = if (facilityItem.isExpanded) {
                        R.drawable.ic_arrow_top
                    } else {
                        R.drawable.ic_arrow_down
                    },
                )
            }

            if (facilityItem.isExpanded) {
                setFacilityOptionAdapter(facilityItem.facilityOptions, position)
                rvFacilityOption.visibility = View.VISIBLE
            } else {
                setFacilityOptionAdapter(emptyList(), position)
                rvFacilityOption.visibility = View.GONE
            }
        }
    }

    private fun getFacilityTypeDrawable(facilityTypeEnum: FacilityTypeEnum): Int {
        return when (facilityTypeEnum) {
            FacilityTypeEnum.Property -> R.drawable.ic_home
            FacilityTypeEnum.Rooms -> R.drawable.ic_number_list
            FacilityTypeEnum.Others -> R.drawable.ic_folder_special
            FacilityTypeEnum.Invalid -> android.R.drawable.stat_notify_error
        }
    }

    private fun setOnClickListener(facilityItem: FacilityType, position: Int) {
        with(binding) {
            root.setOnClickListener {
                onItemClick?.invoke(
                    facilityItem,
                    position,
                )
            }
        }
    }

    private fun setFacilityOptionAdapter(facilityOptions: List<FacilityOption>, parentPosition: Int) {
        childAdapter = FacilityOptionAdapter()
        childAdapter?.onItemSelect = { option, childPosition ->
            onItemChecked?.invoke(option, parentPosition, childPosition)
        }
        binding.rvFacilityOption.apply {
            adapter = childAdapter
            layoutManager =
                LinearLayoutManager(
                    this.context,
                    LinearLayoutManager.VERTICAL,
                    false,
                )
        }
        childAdapter?.submitList(facilityOptions)
    }
}
