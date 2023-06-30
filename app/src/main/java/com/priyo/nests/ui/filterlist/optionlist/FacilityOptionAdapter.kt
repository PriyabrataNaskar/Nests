package com.priyo.nests.ui.filterlist.optionlist

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.priyo.nests.domain.model.FacilityOption

class FacilityOptionAdapter() :
    RecyclerView.Adapter<FacilityOptionViewHolder>() {

    var onItemSelect: ((FacilityOption, Int) -> Unit)? = null
    private var optionList = emptyList<FacilityOption>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FacilityOptionViewHolder {
        parent.context
        return FacilityOptionViewHolder.create(parent, onItemSelect)
    }

    override fun getItemCount(): Int {
        return optionList.size
    }

    override fun onBindViewHolder(
        holder: FacilityOptionViewHolder,
        position: Int,
    ) {
        holder.bindData(
            optionList[position],
            position,
        )
    }

    fun submitList(optionList: List<FacilityOption>) {
        optionList.let {
            val diffUtil = FacilityOptionDiffUtils(optionList, it)
            val diffResult = DiffUtil.calculateDiff(diffUtil)
            this.optionList = it.toList()
            diffResult.dispatchUpdatesTo(this)
        }
    }
}
