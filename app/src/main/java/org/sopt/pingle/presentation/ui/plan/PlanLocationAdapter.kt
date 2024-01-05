package org.sopt.pingle.presentation.ui.plan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.databinding.ItemPlanLocationBinding
import org.sopt.pingle.presentation.model.PlanLocationModel

class PlanLocationAdapter :
    ListAdapter<PlanLocationModel, PlanLocationAdapter.PlanLocationViewHolder>(
        PlanLocationDiffCallback(),
    ) {
    private var planLocationList: List<PlanLocationModel> = emptyList()
    private var selectedPosition: Int = -1
    private lateinit var itemClickListener: ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanLocationViewHolder =
        PlanLocationViewHolder(
            ItemPlanLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )

    override fun onBindViewHolder(holder: PlanLocationViewHolder, position: Int) {
        holder.onBind(getItem(position), position)
    }

    inner class PlanLocationViewHolder(
        private val binding: ItemPlanLocationBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: PlanLocationModel, position: Int) {
            binding.planLocationItem = item
            val planLocationItem = binding.planLocationItem
            planLocationItem?.isSelected = position == selectedPosition

            binding.root.setOnClickListener {
                selectedPosition = position
                itemClickListener.onClick(it, item)
            }
        }
    }

    interface ItemClickListener {
        fun onClick(view: View, item: PlanLocationModel)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }
}

/*fun moveItem(fromPosition: Int, toPosition: Int) {
                val newList = currentList.toMutableList()
                Collections.swap(newList, fromPosition, toPosition)
                submitList(newList)
            }

            fun removeItem(position: Int) {
                val newList = currentList.toMutableList()
                newList.removeAt(position)
                submitList(newList)
            }*/
