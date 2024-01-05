package org.sopt.pingle.presentation.ui.plan

import androidx.recyclerview.widget.DiffUtil
import org.sopt.pingle.presentation.model.PlanLocationModel

class PlanLocationDiffCallback : DiffUtil.ItemCallback<PlanLocationModel>() {

    // Referential equality를 갖는지 판정
    override fun areItemsTheSame(oldItem: PlanLocationModel, newItem: PlanLocationModel): Boolean {
        return oldItem === newItem
    }

    // Structural equality를 갖는지 판정
    override fun areContentsTheSame(
        oldItem: PlanLocationModel,
        newItem: PlanLocationModel,
    ): Boolean {
        return oldItem == newItem
    }
}
