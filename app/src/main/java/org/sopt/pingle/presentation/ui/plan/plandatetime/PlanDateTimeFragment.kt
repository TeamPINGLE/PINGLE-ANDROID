package org.sopt.pingle.presentation.ui.plan.plandatetime

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Locale
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentPlanDateTimeBinding
import org.sopt.pingle.presentation.ui.plan.PlanViewModel
import org.sopt.pingle.util.base.BindingFragment
import org.sopt.pingle.util.component.PingleSnackbar

@AndroidEntryPoint
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

    private fun onDateDialogFragmentClosed(year: Int, month: Int, day: Int) {
        val dateFormat = SimpleDateFormat(DATE_FORMAT)
        val todayLocalDate = dateFormat.parse(LocalDate.now().toString())
        val selectedFormatDate = String.format(SELECTED_DATE_FORMAT, year, month, day)
        val selectedLocalDate = dateFormat.parse(selectedFormatDate)

        if (selectedLocalDate != null) {
            if (selectedLocalDate.before(todayLocalDate)) {
                PingleSnackbar.makeSnackbar(
                    binding.root,
                    getString(R.string.plan_future_date_snackbar),
                    SNACKBAR_BOTTOM_MARGIN
                )
            } else {
                binding.includePlanTextWithTitleDate.tvText.text = String.format(
                    PRINT_DATE_FORMAT,
                    year,
                    month,
                    day
                )
                planViewModel.setPlanDate(selectedFormatDate)
            }
        }
    }

    private fun onTimeDialogFragmentClosed(meridiem: String, hour: Int, minute: Int) {
        val time12HourFormat = String.format(HOUR12_FORMAT, hour, minute, meridiem)
        val time24Hour = convert24HFormatHours(time12HourFormat, false)
        val time24HourWithSecond = convert24HFormatHours(time12HourFormat, true)

        when (planViewModel.selectedTimeType.value) {
            START_TIME -> {
                if (planViewModel.endTime.value.isNotBlank() && time24Hour > planViewModel.endTime.value) {
                    PingleSnackbar.makeSnackbar(
                        binding.root,
                        getString(R.string.plan_later_time_snackbar),
                        SNACKBAR_BOTTOM_MARGIN
                    )
                } else {
                    binding.includePlanTextWithTitleStartTime.tvText.text =
                        time24Hour
                    planViewModel.setStartTime(time24HourWithSecond)
                }
            }

            END_TIME -> {
                if (planViewModel.startTime.value.isNotBlank() && time24Hour < planViewModel.startTime.value) {
                    PingleSnackbar.makeSnackbar(
                        binding.root,
                        getString(R.string.plan_later_time_snackbar),
                        SNACKBAR_BOTTOM_MARGIN
                    )
                } else {
                    binding.includePlanTextWithTitleEndTime.tvText.text =
                        time24Hour
                    planViewModel.setEndTime(time24HourWithSecond)
                }
            }
        }
    }

    private fun convert24HFormatHours(time12Hour: String, isWithSecond: Boolean): String {
        val inputFormat = SimpleDateFormat(SIMPLE_DATE_INPUT, Locale.US)
        val outputFormat = SimpleDateFormat(SIMPLE_DATE_OUTPUT, Locale.getDefault())
        val outputFormatWithSecond = SimpleDateFormat(TIME_FORMAT_OUTPUT, Locale.getDefault())

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
        const val SIMPLE_DATE_INPUT = "hh:mm a"
        const val SIMPLE_DATE_OUTPUT = "HH:mm"
        const val TIME_FORMAT_OUTPUT = "HH:mm:ss"
        const val START_TIME = "startTime"
        const val END_TIME = "endTime"
        const val SNACKBAR_BOTTOM_MARGIN = 126
        const val DATE_FORMAT = "yyyy-MM-dd"
        const val SELECTED_DATE_FORMAT = "%d-%02d-%02d"
        const val PRINT_DATE_FORMAT = "%d년 %d월 %d일"
        const val HOUR12_FORMAT = "%02d:%02d %s"
    }
}
