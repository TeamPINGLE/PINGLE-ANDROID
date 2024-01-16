package org.sopt.pingle.presentation.ui.plan.planlocation

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentPlanLocationBinding
import org.sopt.pingle.presentation.ui.plan.PlanViewModel
import org.sopt.pingle.util.base.BindingFragment
import org.sopt.pingle.util.context.hideKeyboard
import org.sopt.pingle.util.view.UiState

@AndroidEntryPoint
class PlanLocationFragment :
    BindingFragment<FragmentPlanLocationBinding>(R.layout.fragment_plan_location) {
    private val planLocationViewModel by activityViewModels<PlanViewModel>()
    private val planLocationAdapter: PlanLocationAdapter by lazy {
        PlanLocationAdapter(::deleteOldPosition)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        addListeners()
        collectData()
    }

    private fun initLayout() {
        binding.rvPlanLocationList.adapter = planLocationAdapter
    }

    private fun addListeners() {
        binding.root.setOnClickListener {
            requireContext().hideKeyboard(binding.etPlanLocationSearch)
        }

        binding.ivPlanLocationSearchBtn.setOnClickListener {
            if (binding.etPlanLocationSearch.text.isNotBlank()) {
                planLocationViewModel.getPlanLocationList(binding.etPlanLocationSearch.text.toString())
            }
            requireContext().hideKeyboard(binding.etPlanLocationSearch)
        }

        binding.etPlanLocationSearch.setOnKeyListener(
            View.OnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                    if (binding.etPlanLocationSearch.text.isNotBlank()) {
                        planLocationViewModel.getPlanLocationList(binding.etPlanLocationSearch.text.toString())
                    }
                    requireContext().hideKeyboard(binding.etPlanLocationSearch)
                    return@OnKeyListener true
                }
                false
            }
        )
    }

    private fun collectData() {
        planLocationViewModel.planLocationListState.flowWithLifecycle(lifecycle).onEach { uiState ->
            when (uiState) {
                is UiState.Success -> {
                    planLocationAdapter.submitList(uiState.data)
                    planLocationAdapter.currentList
                    binding.layoutPlanLocationEmpty.visibility = View.INVISIBLE
                }

                is UiState.Empty -> {
                    planLocationAdapter.submitList(null)
                    binding.layoutPlanLocationEmpty.visibility = View.VISIBLE
                }

                else -> Unit
            }
        }.launchIn(lifecycleScope)
    }

    private fun deleteOldPosition(position: Int) {
        planLocationViewModel.updatePlanLocationList(position)
    }

    override fun onDestroyView() {
        binding.rvPlanLocationList.adapter = null
        super.onDestroyView()
    }
}
