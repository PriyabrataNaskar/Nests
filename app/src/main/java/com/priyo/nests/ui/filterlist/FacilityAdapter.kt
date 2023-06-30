package com.priyo.nests.ui.filterlist

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.priyo.nests.domain.model.FacilityOption
import com.priyo.nests.domain.model.FacilityType

class FacilityAdapter() :
    RecyclerView.Adapter<FacilityViewHolder>() {

    var onItemClick: ((FacilityType, Int) -> Unit)? = null
    var onItemChecked: ((FacilityOption, Int, Int) -> Unit)? = null
    var oldFacilityTypeList = emptyList<FacilityType>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FacilityViewHolder {
        parent.context
        return FacilityViewHolder.create(parent, onItemClick, onItemChecked)
    }

    override fun getItemCount(): Int {
        return oldFacilityTypeList.size
    }

    override fun onBindViewHolder(
        holder: FacilityViewHolder,
        position: Int,
    ) {
        holder.bindData(
            oldFacilityTypeList[position],
            position,
        )
    }

    fun submitList(facilityList: List<FacilityType>) {
        facilityList.let {
            val diffUtil = FacilityDiffUtils(oldFacilityTypeList, it)
            val diffResult = DiffUtil.calculateDiff(diffUtil)
            oldFacilityTypeList = it.toList()
            diffResult.dispatchUpdatesTo(this)
        }
    }
}
