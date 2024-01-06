package org.sopt.pingle.presentation.ui.main.plan

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ItemPlanLocationBinding
import org.sopt.pingle.domain.model.PlanLocationEntity

private var selectedItemPosition = -1

class PlanLocationAdapter :
    ListAdapter<PlanLocationEntity, PlanLocationViewHolder>(
        PlanLocationDiffCallback(),
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanLocationViewHolder =
        PlanLocationViewHolder(
            ItemPlanLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )

    override fun onBindViewHolder(holder: PlanLocationViewHolder, position: Int) {
        holder.onBind(getItem(position))

        if (position == selectedItemPosition) {
            holder.onItemSelected()
        } else {
            holder.onItemDeselected()
        }
    }
}

class PlanLocationViewHolder(
    private val binding: ItemPlanLocationBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: PlanLocationEntity) {
        binding.planLocationItem = item

        binding.root.setOnClickListener {
            toggleItemSelection(item)
        }
    }

    private fun toggleItemSelection(item: PlanLocationEntity) {
        item.isSelected = !item.isSelected
        selectedItemPosition = if (item.isSelected) adapterPosition else -1
        // 아이템 변경 알림

        Log.d("abc", item.isSelected.toString())
    }

    fun onItemSelected() {
        val checkImg = R.drawable.ic_all_check_selected_24
        with(binding) {
            imvPlanLocationCheck.setImageResource(checkImg)
            imvPlanLocationCheck.isSelected = true
        }
    }

    fun onItemDeselected() {
        val defaultImg = R.drawable.ic_all_check_default_24
        with(binding) {
            imvPlanLocationCheck.setImageResource(defaultImg)
            imvPlanLocationCheck.isSelected = false
        }
    }
}
