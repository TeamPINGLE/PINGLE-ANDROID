package org.sopt.pingle.presentation.ui.plan.planlocation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.databinding.ItemPlanLocationBinding
import org.sopt.pingle.domain.model.PlanLocationEntity
import org.sopt.pingle.util.view.ItemDiffCallback

class PlanLocationAdapter(
    private val setOldItem: (Int) -> Unit
) :
    ListAdapter<PlanLocationEntity, PlanLocationViewHolder>(
        ItemDiffCallback<PlanLocationEntity>(
            onItemsTheSame = { old, new -> old.location == new.location },
            onContentsTheSame = { old, new -> old == new }
        )
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanLocationViewHolder =
        PlanLocationViewHolder(
            ItemPlanLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            setOldItem
        )

    override fun onBindViewHolder(holder: PlanLocationViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
