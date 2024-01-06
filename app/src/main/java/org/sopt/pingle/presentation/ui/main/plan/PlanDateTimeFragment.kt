package org.sopt.pingle.presentation.ui.main.plan

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import java.text.SimpleDateFormat
import java.time.LocalDate
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentPlanDateTimeBinding
import org.sopt.pingle.util.base.BindingFragment
import timber.log.Timber

class PlanDateTimeFragment :
    BindingFragment<FragmentPlanDateTimeBinding>(R.layout.fragment_plan_date_time) {
    private val planViewModel: PlanViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addListeners()
    }

    private fun addListeners() {
        binding.includePlanTextWithTitleDate.root.setOnClickListener {
            val dialogDateFragment = PlanDateDialogFragment(::onDateDialogFragmentClosed)
            dialogDateFragment.show(
                parentFragmentManager,
                dialogDateFragment.tag
            )
        }

        binding.includePlanTextWithTitleStartTime.root.setOnClickListener {
            val dialogStartTimeFragment = PlanTimeDialogFragment(::onTimeDialogFragmentClosed)
            dialogStartTimeFragment.show(
                parentFragmentManager,
                dialogStartTimeFragment.tag
            )
            planViewModel.setSelectedTimeType(START_TIME)
        }

        binding.includePlanTextWithTitleEndTime.root.setOnClickListener {
            val dialogEndTimeFragment = PlanTimeDialogFragment(::onTimeDialogFragmentClosed)
            dialogEndTimeFragment.show(
                parentFragmentManager,
                dialogEndTimeFragment.tag
            )
            planViewModel.setSelectedTimeType(END_TIME)
        }
    }

    // TODO format 관련 refactor
    private fun onDateDialogFragmentClosed(year: Int, month: Int, day: Int) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val todayLocalDate = dateFormat.parse(LocalDate.now().toString())
        val selectedFormatDate = String.format("%d-%02d-%02d", year, month, day)
        val selectedLocalDate = dateFormat.parse(selectedFormatDate)

        if (selectedLocalDate != null) {
            if (selectedLocalDate.before(todayLocalDate)) {
                // TODO 에러 스낵바 노출
            } else {
                binding.includePlanTextWithTitleDate.tvText.text = String.format(
                    "%d년 %d월 %d일",
                    year,
                    month,
                    day
                )
                planViewModel.setPlanDate(selectedFormatDate)
            }
        }
    }

    // TODO 시간 포맷 나오면 수정 예정
    // TODO {} 수정 예정
    private fun onTimeDialogFragmentClosed(time: String) {
        when (planViewModel.selectedTimeType.value) {
            START_TIME -> {
                Timber.d(START_TIME)
            }

            END_TIME -> {
                Timber.d(END_TIME)
            }
        }
    }

    companion object {
        const val START_TIME = "startTime"
        const val END_TIME = "endTime"
    }
}
