package org.sopt.pingle.presentation.ui.main.plan.planrecruitment

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
import org.sopt.pingle.databinding.FragmentPlanRecruitmentBinding
import org.sopt.pingle.presentation.ui.main.plan.PlanViewModel
import org.sopt.pingle.util.base.BindingFragment
import org.sopt.pingle.util.context.hideKeyboard

@AndroidEntryPoint
class PlanRecruitmentFragment :
    BindingFragment<FragmentPlanRecruitmentBinding>(R.layout.fragment_plan_recruitment) {

    private val viewModel: PlanViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.planViewModel = viewModel

        addListeners()
        collectData()
    }

    private fun addListeners() {
        with(binding) {
            root.setOnClickListener {
                requireActivity().hideKeyboard(it)
                viewModel.setSelectedRecruitment(etPlanRecruitmentInputNumber.text.toString())
            }

            etPlanRecruitmentInputNumber.setOnKeyListener(
                View.OnKeyListener { _, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                        viewModel.setSelectedRecruitment(etPlanRecruitmentInputNumber.text.toString())
                        return@OnKeyListener true
                    }
                    false
                }
            )

            btnPlanRecruitmentPlus.setOnClickListener {
                viewModel.incRecruitmentNum()
            }

            btnPlanRecruitmentMinus.setOnClickListener {
                viewModel.decRecruitmentNum()
            }
        }
    }

    private fun collectData() {
        viewModel.selectedRecruitment.flowWithLifecycle(lifecycle).onEach { selectedRecruitment ->
            when (selectedRecruitment.toString()) {
                MAX_RECRUITMENT -> binding.btnPlanRecruitmentPlus.isEnabled = false
                MIN_RECRUITMENT -> binding.btnPlanRecruitmentMinus.isEnabled = false
                else -> {
                    binding.btnPlanRecruitmentPlus.isEnabled = true
                    binding.btnPlanRecruitmentMinus.isEnabled = true
                }
            }
        }.launchIn(lifecycleScope)
    }

    companion object {
        const val MAX_RECRUITMENT = "99"
        const val MIN_RECRUITMENT = "1"
    }
}
