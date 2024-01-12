package org.sopt.pingle.presentation.ui.plan.planlocation

import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.databinding.ItemPlanLocationBinding
import org.sopt.pingle.domain.model.PlanLocationEntity

class PlanLocationViewHolder(
    private val binding: ItemPlanLocationBinding,
    private val setOldItem: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: PlanLocationEntity) {
        binding.planLocationItem = item

        binding.root.setOnClickListener {
            setOldItem(bindingAdapterPosition)
        }
    }
}
