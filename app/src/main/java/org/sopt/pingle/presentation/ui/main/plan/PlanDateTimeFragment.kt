package org.sopt.pingle.presentation.ui.main.plan

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Locale
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentPlanDateTimeBinding
import org.sopt.pingle.util.base.BindingFragment
import org.sopt.pingle.util.component.CustomSnackbar

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
                CustomSnackbar.makeSnackbar(
                    binding.root,
                    getString(R.string.plan_future_date_snackber),
                    126
                )
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

    private fun onTimeDialogFragmentClosed(meridiem: String, hour: Int, minute: Int) {
        val time12Hour = String.format("%02d:%02d %s", hour, minute, meridiem)
        when (planViewModel.selectedTimeType.value) {
            START_TIME -> {
                binding.includePlanTextWithTitleStartTime.tvText.text =
                    convert24HFormatHours(time12Hour, false)
                planViewModel.setStartTime(convert24HFormatHours(time12Hour, true))
            }

            END_TIME -> {
                binding.includePlanTextWithTitleEndTime.tvText.text =
                    convert24HFormatHours(time12Hour, false)
                planViewModel.setEndTime(convert24HFormatHours(time12Hour, true))
            }
        }
    }

    private fun convert24HFormatHours(time12Hour: String, isWithSecond: Boolean): String {
        val inputFormat = SimpleDateFormat("hh:mm a", Locale.US)
        val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val outputFormatWithSecond = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

        val date = inputFormat.parse(time12Hour)

        return when (isWithSecond) {
            false -> {
                outputFormat.format(date)
            }

            true -> {
                outputFormatWithSecond.format(date)
            }
        }
    }

    companion object {
        const val START_TIME = "startTime"
        const val END_TIME = "endTime"
    }
}
