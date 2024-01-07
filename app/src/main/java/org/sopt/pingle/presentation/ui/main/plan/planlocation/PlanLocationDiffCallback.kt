package org.sopt.pingle.presentation.ui.main.plan.planlocation

import androidx.recyclerview.widget.DiffUtil
import org.sopt.pingle.domain.model.PlanLocationEntity

class PlanLocationDiffCallback : DiffUtil.ItemCallback<PlanLocationEntity>() {

    // Referential equality를 갖는지 판정
    override fun areItemsTheSame(oldItem: PlanLocationEntity, newItem: PlanLocationEntity): Boolean {
        return oldItem === newItem
    }

    // Structural equality를 갖는지 판정
    override fun areContentsTheSame(
        oldItem: PlanLocationEntity,
        newItem: PlanLocationEntity,
    ): Boolean {
        return oldItem == newItem
    }
}
