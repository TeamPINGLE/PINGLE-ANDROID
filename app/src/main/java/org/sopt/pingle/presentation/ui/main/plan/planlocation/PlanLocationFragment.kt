package org.sopt.pingle.presentation.ui.main.plan.planlocation

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
import org.sopt.pingle.presentation.ui.main.plan.PlanViewModel
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

        addListeners()
        collectData()
    }

    private fun addListeners() {
        binding.ivPlanLocationSearchBtn.setOnClickListener {
            planLocationViewModel.getPlanLocationList(binding.etPlanLocationSearch.text.toString())
        }

        val searchListener = binding.etPlanLocationSearch
        searchListener.setOnKeyListener(
            View.OnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                    planLocationViewModel.getPlanLocationList(binding.etPlanLocationSearch.text.toString())
                    requireActivity().hideKeyboard(searchListener)
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
                    binding.rvPlanLocationList.adapter = planLocationAdapter
                    planLocationAdapter.submitList(uiState.data)
                    planLocationAdapter.currentList

                    with(binding) {
                        rvPlanLocationList.visibility = View.VISIBLE
                        layoutPlanLocationEmpty.visibility = View.INVISIBLE
                    }
                }

                is UiState.Empty -> {
                    with(binding) {
                        rvPlanLocationList.visibility = View.INVISIBLE
                        layoutPlanLocationEmpty.visibility = View.VISIBLE
                    }
                }

                else -> Unit
            }
        }.launchIn(lifecycleScope)
    }

    private fun deleteOldPosition(position: Int) {
        planLocationViewModel.updatePlanLocationList(position)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvPlanLocationList.adapter = null
    }
}
