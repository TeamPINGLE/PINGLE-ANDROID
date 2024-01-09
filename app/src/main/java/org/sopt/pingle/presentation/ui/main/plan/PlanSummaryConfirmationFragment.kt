package org.sopt.pingle.presentation.ui.main.plan

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentPlanSummaryConfirmationBinding
import org.sopt.pingle.presentation.type.CategoryType
import org.sopt.pingle.util.base.BindingFragment
import org.sopt.pingle.util.fragment.colorOf

class PlanSummaryConfirmationFragment :
    BindingFragment<FragmentPlanSummaryConfirmationBinding>(R.layout.fragment_plan_summary_confirmation) {
    private val viewModel by activityViewModels<PlanViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
    }

    private fun initLayout() {
        val category: CategoryType? = viewModel.selectedCategory.value
        if (category != null) {
            with(binding) {
                badgePlanSummaryConfirmationCategory.setBadgeCategoryType(category)
                tvPlanSummaryConfirmationName.setTextColor(colorOf((category.textColor)))
                tvPlanSummaryConfirmationName.text = viewModel.planTitle.value
                tvPlanSummaryConfirmationOwnerName.text = "개최자"
                tvPlanSummaryConfirmationCalenderDetail.text =
                    viewModel.planDate.value + "\n" + viewModel.startTime.value + " ~ " + viewModel.endTime.value
                tvPlanSummaryConfirmationMapDetail.text = viewModel.selectedLocation.value?.location
                tvPlanSummaryConfirmationRecruitmentDetail.text = getString(R.string.plan_summary_confirmation_recruitment_number, viewModel.selectedRecruitment.value)
            }
        }
    }
}
