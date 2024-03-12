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
    private val planLocationAdapter: PlanLocationAdapter by lazy { PlanLocationAdapter(::deleteOldPosition) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        addListeners()
        collectData()
    }

    override fun onDestroyView() {
        binding.rvPlanLocationList.adapter = null
        super.onDestroyView()
    }

    private fun initLayout() {
        binding.rvPlanLocationList.adapter = planLocationAdapter
    }

    private fun addListeners() {
        (binding.pingleSearchPlanLocation.binding.etSearchPingleEditText).let { searchEditText ->
            binding.root.setOnClickListener {
                requireContext().hideKeyboard(searchEditText)
            }

            searchEditText.setOnKeyListener(
                View.OnKeyListener { _, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                        if (searchEditText.text.isNotBlank()) {
                            planLocationViewModel.getPlanLocationList(searchEditText.text.toString())
                        }
                        requireContext().hideKeyboard(searchEditText)
                        return@OnKeyListener true
                    }
                    false
                }
            )
        }
    }

    private fun collectData() {
        planLocationViewModel.planLocationListState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { uiState ->
                when (uiState) {
                    is UiState.Success -> {
                        planLocationAdapter.submitList(uiState.data)
                        binding.layoutPlanLocationEmpty.visibility = View.INVISIBLE
                    }

                    is UiState.Empty -> {
                        planLocationAdapter.submitList(null)
                        binding.layoutPlanLocationEmpty.visibility = View.VISIBLE
                    }

                    else -> Unit
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun deleteOldPosition(position: Int) {
        planLocationViewModel.updatePlanLocationList(position)
    }
}
