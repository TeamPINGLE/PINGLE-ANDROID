package org.sopt.pingle.presentation.ui.main.plan

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentPlanSummaryConfirmationBinding
import org.sopt.pingle.presentation.type.CategoryType
import org.sopt.pingle.util.base.BindingFragment
import org.sopt.pingle.util.fragment.colorOf

class PlanSummaryConfirmationFragment :
    BindingFragment<FragmentPlanSummaryConfirmationBinding>(R.layout.fragment_plan_summary_confirmation) {
    private val viewModel by viewModels<PlanViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.planViewModel = viewModel
        binding.lifecycleOwner = this

        initLayout()
    }

    private fun initLayout() {
        val category: CategoryType? = viewModel.selectedCategory.value
        if (category != null) {
            binding.badgePlanSummaryConfirmationCategory.setBadgeCategoryType(category)
            binding.tvPlanSummaryConfirmationName.text = viewModel.planTitle.value
            binding.tvPlanSummaryConfirmationName.setTextColor(colorOf((category.textColor)))
            binding.tvPlanSummaryConfirmationMapDetail.text = viewModel.location.value
            binding.tvPlanSummaryConfirmationCalenderDetail.text = viewModel.planDate.value
            // tvPlanSummaryConfirmationRecruitmentDetail.text = planViewModel.recruitment...valu
        }
    }
}
