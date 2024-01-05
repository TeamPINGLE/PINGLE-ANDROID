package org.sopt.pingle.presentation.ui.plan

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentPlanLocationBinding
import org.sopt.pingle.presentation.model.PlanLocationModel
import org.sopt.pingle.util.base.BindingFragment

class PlanLocationFragment :
    BindingFragment<FragmentPlanLocationBinding>(R.layout.fragment_plan_location) {
    private val planLocationViewModel by viewModels<PlanLocationViewModel>()
    private val planLocationAdapter: PlanLocationAdapter by lazy {
        PlanLocationAdapter()
    }

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
        planLocationAdapter.setItemClickListener(object : PlanLocationAdapter.ItemClickListener {
            override fun onClick(view: View, item: PlanLocationModel) {
                // TODO click
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvPlanLocationList.adapter = null
    }
}
