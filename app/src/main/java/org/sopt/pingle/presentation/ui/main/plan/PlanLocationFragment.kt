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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        addListeners()
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

    private fun addListeners() {
        binding.ivPlanLocationSearchBtn.setOnClickListener {
            checkListExist()
        }
    }

    private fun deleteOldPosition(position: Int) {
        planLocationViewModel.updatePlanLocationList(position)
    }

    private fun checkListExist() = if (planLocationViewModel.checkIsNull()) {
        with(binding) {
            rvPlanLocationList.visibility = View.INVISIBLE
            layoutPlanLocationEmpty.visibility = View.VISIBLE
        }
    } else {
        with(binding) {
            rvPlanLocationList.visibility = View.VISIBLE
            layoutPlanLocationEmpty.visibility = View.INVISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvPlanLocationList.adapter = null
    }
}
