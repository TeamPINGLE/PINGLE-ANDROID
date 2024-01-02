package org.sopt.pingle.presentation.ui.plan

import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.databinding.ItemPlanLocationBinding
import org.sopt.pingle.presentation.model.PlanLocationModel

class PlanLocationViewHolder(private val binding: ItemPlanLocationBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(locationData: PlanLocationModel) {
        binding.tvPlanLocationName.text = locationData.location
        binding.tvPlanLocationAddress.text = locationData.address
    }
}
