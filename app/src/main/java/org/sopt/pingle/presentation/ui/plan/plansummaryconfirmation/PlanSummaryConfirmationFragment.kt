package org.sopt.pingle.presentation.ui.plan.plansummaryconfirmation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentPlanSummaryConfirmationBinding
import org.sopt.pingle.presentation.ui.plan.PlanViewModel
import org.sopt.pingle.util.base.BindingFragment
import org.sopt.pingle.util.convertToCalenderDetail
import org.sopt.pingle.util.fragment.colorOf
import org.sopt.pingle.util.view.UiState

@AndroidEntryPoint
class PlanSummaryConfirmationFragment :
    BindingFragment<FragmentPlanSummaryConfirmationBinding>(R.layout.fragment_plan_summary_confirmation) {
    private val viewModel by activityViewModels<PlanViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        collectData()
    }

    private fun initLayout() {
        viewModel.getUserInfo()

        with(binding) {
            viewModel.selectedCategory.value?.let { category ->
                badgePlanSummaryConfirmationCategory.setBadgeCategoryType(category)
                tvPlanSummaryConfirmationName.setTextColor(colorOf((category.textColor)))
            }
            tvPlanSummaryConfirmationName.text = viewModel.planTitle.value
            tvPlanSummaryConfirmationCalenderDetail.text = convertToCalenderDetail(
                date = viewModel.planDate.value,
                startAt = viewModel.startTime.value,
                endAt = viewModel.endTime.value
            )
            tvPlanSummaryConfirmationMapDetail.text = viewModel.selectedLocation.value?.location
            tvPlanSummaryConfirmationRecruitmentDetail.text = getString(
                R.string.plan_summary_confirmation_recruitment_number,
                viewModel.selectedRecruitment.value
            )
        }
    }

    private fun collectData() {
        viewModel.userInfoState.flowWithLifecycle(lifecycle).onEach { userInfoState ->
            when (userInfoState) {
                is UiState.Success ->
                    binding.tvPlanSummaryConfirmationOwnerName.text =
                        userInfoState.data.name

                else -> Unit
            }
        }.launchIn(lifecycleScope)
    }
}
