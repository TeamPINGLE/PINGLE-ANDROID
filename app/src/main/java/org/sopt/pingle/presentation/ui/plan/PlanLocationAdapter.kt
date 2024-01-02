package org.sopt.pingle.presentation.ui.plan

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.databinding.ItemPlanLocationBinding
import org.sopt.pingle.presentation.model.PlanLocationModel

class PlanLocationAdapter(context: Context) : RecyclerView.Adapter<PlanLocationViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    private var planLocationList: List<PlanLocationModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanLocationViewHolder {
        val binding = ItemPlanLocationBinding.inflate(inflater, parent, false)
        return PlanLocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlanLocationViewHolder, position: Int) {
        holder.onBind(planLocationList[position])
    }

    override fun getItemCount() = planLocationList.size

    fun setPlanLocationList(planLocationList: List<PlanLocationModel>) {
        this.planLocationList = planLocationList.toList()
        notifyDataSetChanged()
    }
}
