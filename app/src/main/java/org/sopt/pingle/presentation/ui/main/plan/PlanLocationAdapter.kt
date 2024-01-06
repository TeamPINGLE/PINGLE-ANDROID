package org.sopt.pingle.presentation.ui.main.plan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.databinding.ItemPlanLocationBinding
import org.sopt.pingle.domain.model.PlanLocationEntity

class PlanLocationAdapter(
    private val setOldItem: (Int) -> Unit,
) :
    ListAdapter<PlanLocationEntity, PlanLocationViewHolder>(
        PlanLocationDiffCallback(),
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanLocationViewHolder =
        PlanLocationViewHolder(
            ItemPlanLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            setOldItem,
        )

    override fun onBindViewHolder(holder: PlanLocationViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}

class PlanLocationViewHolder(
    private val binding: ItemPlanLocationBinding,
    private val setOldItem: (Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: PlanLocationEntity) {
        binding.planLocationItem = item

        binding.root.setOnClickListener {
            setOldItem(bindingAdapterPosition)
        }
    }
}
