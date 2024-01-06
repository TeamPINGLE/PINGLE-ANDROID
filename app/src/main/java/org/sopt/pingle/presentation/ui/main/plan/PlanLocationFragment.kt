package org.sopt.pingle.presentation.ui.main.plan

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentPlanLocationBinding
import org.sopt.pingle.util.base.BindingFragment

class PlanLocationFragment :
    BindingFragment<FragmentPlanLocationBinding>(R.layout.fragment_plan_location) {
    private val planLocationViewModel by viewModels<PlanLocationViewModel>()
    private val planLocationAdapter: PlanLocationAdapter by lazy {
        PlanLocationAdapter(::deleteOldPosition)
    }

    private var oldPosition = -1
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }

    private fun initLayout() {
        binding.rvPlanLocationList.apply {
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.HORIZONTAL,
                ),
            )
            this.layoutManager = layoutManager
            adapter = planLocationAdapter
        }
        planLocationAdapter.submitList(planLocationViewModel.mockPlanLocationList)
    }

    private fun deleteOldPosition(position: Int) {
        if (oldPosition == -1 && oldPosition != position) {
            planLocationViewModel.mockPlanLocationList[position].isSelected.set(true)
        } else if (oldPosition == position) {
            planLocationViewModel.mockPlanLocationList[oldPosition].isSelected.set(false)
        } else {
            planLocationViewModel.mockPlanLocationList[position].isSelected.set(true)
            planLocationViewModel.mockPlanLocationList[oldPosition].isSelected.set(false)
        }
        oldPosition = position
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvPlanLocationList.adapter = null
    }
}
