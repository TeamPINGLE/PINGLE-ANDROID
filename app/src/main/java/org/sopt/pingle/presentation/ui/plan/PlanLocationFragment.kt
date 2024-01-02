package org.sopt.pingle.presentation.ui.plan

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentPlanLocationBinding
import org.sopt.pingle.util.base.BindingFragment

class PlanLocationFragment :
    BindingFragment<FragmentPlanLocationBinding>(R.layout.fragment_plan_location) {
    private val planLocationViewModel by viewModels<PlanLocationViewModel>()
    private lateinit var planLocationAdapter: PlanLocationAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
    }

    private fun initLayout() {
        planLocationAdapter = PlanLocationAdapter(requireContext())
        binding.rvPlanLocationList.adapter = planLocationAdapter
        planLocationAdapter.setPlanLocationList(planLocationViewModel.mockPlanLocationList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
