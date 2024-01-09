package org.sopt.pingle.presentation.ui.main.plan.planlocation

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentPlanLocationBinding
import org.sopt.pingle.presentation.ui.main.plan.PlanViewModel
import org.sopt.pingle.util.base.BindingFragment
import org.sopt.pingle.util.context.hideKeyboard

class PlanLocationFragment :
    BindingFragment<FragmentPlanLocationBinding>(R.layout.fragment_plan_location) {
    private val planLocationViewModel by viewModels<PlanViewModel>()
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
            this.layoutManager = LinearLayoutManager(context)
            adapter = planLocationAdapter
            /*addItemDecoration(
                PlanLocationDivider(1, R.color.g_09),
            )*/
        }
        planLocationAdapter.submitList(planLocationViewModel.planLocationList.value)
    }

    private fun addListeners() {
        binding.ivPlanLocationSearchBtn.setOnClickListener {
            checkListExist()
        }

        val searchListener = binding.etPlanLocationSearch
        searchListener.setOnKeyListener(
            View.OnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                    checkListExist()
                    requireActivity().hideKeyboard(searchListener)
                    return@OnKeyListener true
                }
                false
            }
        )
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
