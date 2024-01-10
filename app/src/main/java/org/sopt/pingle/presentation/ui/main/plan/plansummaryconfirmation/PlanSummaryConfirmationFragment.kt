package org.sopt.pingle.presentation.ui.main.plan.plansummaryconfirmation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentPlanSummaryConfirmationBinding
import org.sopt.pingle.presentation.ui.main.plan.PlanViewModel
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
        viewModel.selectedCategory.value?.let { category ->
            with(binding) {
                badgePlanSummaryConfirmationCategory.setBadgeCategoryType(category)
                tvPlanSummaryConfirmationName.setTextColor(colorOf((category.textColor)))
                tvPlanSummaryConfirmationName.text = viewModel.planTitle.value
                tvPlanSummaryConfirmationOwnerName.text = "개최자"
                tvPlanSummaryConfirmationCalenderDetail.text =
                    convertDateFormat(viewModel.planDate.value) + "\n" + convertTimeFormat(viewModel.startTime.value) + " ~ " + convertTimeFormat(
                    viewModel.endTime.value
                )
                tvPlanSummaryConfirmationMapDetail.text = viewModel.selectedLocation.value?.location
                tvPlanSummaryConfirmationRecruitmentDetail.text = getString(
                    R.string.plan_summary_confirmation_recruitment_number,
                    viewModel.selectedRecruitment.value
                )
            }
        }
    }

    private fun convertTimeFormat(time: String): String = time.substring(TIME_START_INDEX, TIME_END_INDEX)
    private fun convertDateFormat(date: String): String {
        val year = date.substring(YEAR_START_INDEX, YEAR_END_INDEX)
        val month = date.substring(MONTH_START_INDEX, MONTH_END_INDEX)
        val day = date.substring(DAY_START_INDEX, DAY_END_INDEX)
        return year + YEAR + month + MONTH + day + DAY
    }

    companion object {
        const val TIME_START_INDEX = 0
        const val TIME_END_INDEX = 5
        const val YEAR_START_INDEX = 0
        const val YEAR_END_INDEX = 4
        const val MONTH_START_INDEX = 5
        const val MONTH_END_INDEX = 7
        const val DAY_START_INDEX = 8
        const val DAY_END_INDEX = 10
        const val YEAR = "년 "
        const val MONTH = "월 "
        const val DAY = "일"
    }
}
