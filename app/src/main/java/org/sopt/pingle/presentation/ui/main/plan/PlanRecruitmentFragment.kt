package org.sopt.pingle.presentation.ui.main.plan

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentPlanRecruitmentBinding
import org.sopt.pingle.util.base.BindingFragment
import org.sopt.pingle.util.component.CustomSnackbar
import org.sopt.pingle.util.context.hideKeyboard

class PlanRecruitmentFragment :
    BindingFragment<FragmentPlanRecruitmentBinding>(R.layout.fragment_plan_recruitment) {

    private val planViewModel: PlanViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.planViewModel = planViewModel
        binding.lifecycleOwner = this

        addListeners()
        collectData()
    }

    private fun addListeners() {
        binding.root.setOnClickListener { requireActivity().hideKeyboard(it) }

        binding.btnPlanRecruitmentPlus.setOnClickListener {
            var i = planViewModel.selectedRecruitment.value?.toInt()
            i = i!! + 1
            planViewModel.selectedRecruitment.value = i.toString()
        }

        binding.btnPlanRecruitmentMinus.setOnClickListener {
            var i = planViewModel.selectedRecruitment.value?.toInt()
            i = i!! - 1
            planViewModel.selectedRecruitment.value = i.toString()
        }
    }

    private fun collectData() {
        planViewModel.selectedRecruitment.flowWithLifecycle(lifecycle).onEach {
            when (it.toString()) {
                "99" -> {
                    binding.btnPlanRecruitmentPlus.apply {
                        isEnabled = false
                    }
                    CustomSnackbar.makeSnackbar(
                        binding.layoutPlanRecruitment,
                        getString(R.string.plan_recruitment_snackbar),
                        126
                    )
                }

                "1" -> {
                    binding.btnPlanRecruitmentMinus.isEnabled = false
                }

                else -> {
                    binding.btnPlanRecruitmentPlus.isEnabled = true
                    binding.btnPlanRecruitmentMinus.isEnabled = true
                }
            }
        }.launchIn(lifecycleScope)
    }
}
