package org.sopt.pingle.presentation.ui.plan

/*import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ItemPlanLocationBinding
import org.sopt.pingle.presentation.model.PlanLocationModel

interface ItemClickListener {
    fun onClick(view: View, item: PlanLocationModel)
}
class PlanLocationViewHolder(
    private val binding: ItemPlanLocationBinding,
    private var selectedPosition: Int,
    private var itemClickListener: ItemClickListener
) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: PlanLocationModel, position: Int, selectedPosition: Int) {
        binding.planLocationItem = item

        binding.root.setOnClickListener {
            binding.imvPlanLocationCheck.setImageResource(R.drawable.ic_all_check_selected_24)
            binding.imvPlanLocationCheck.isSelected = position == selectedPosition

            binding.imvPlanLocationCheck.setOnClickListener {
                this.selectedPosition = position
                itemClickListener.onClick(it, item)
            }
        }

        *//*if (selectedPosition == this.adapterPosition) {
            planLocationList[adapterPosition].isSelected = true
            binding.imvPlanLocationCheck.setImageResource(R.drawable.ic_all_check_selected_24)
        } else {
            planLocationList[adapterPosition].isSelected = false
            binding.imvPlanLocationCheck.setImageResource(R.drawable.ic_all_check_default_24)
        }

        if (onItemClickListener != null) {
            binding.root.setOnClickListener {
                onItemClickListener?.onItemClick(item, adapterPosition)
                if (selectedPosition != adapterPosition) {
                    val previousSelectedPosition = selectedPosition
                    selectedPosition = adapterPosition
                    adapter.updateItem(previousSelectedPosition)
                    adapter.updateItem(selectedPosition)
                }
            }
        }*//*
    }
}*/
