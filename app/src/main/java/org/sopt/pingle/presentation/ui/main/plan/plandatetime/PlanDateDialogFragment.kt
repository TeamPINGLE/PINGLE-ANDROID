package org.sopt.pingle.presentation.ui.main.plan.plandatetime

import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import org.sopt.pingle.R
import org.sopt.pingle.databinding.DialogDatePickerBinding
import org.sopt.pingle.util.base.BindingBottomSheetDialogFragment
import java.time.LocalDateTime

class PlanDateDialogFragment(
    private val onDialogClosed: (year: Int, month: Int, day: Int) -> Unit
) :
    BindingBottomSheetDialogFragment<DialogDatePickerBinding>(R.layout.dialog_date_picker) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        val today = LocalDateTime.now()
        val yearPicker = binding.npDatePickerYear
        yearPicker.apply {
            wrapSelectorWheel = false // 순한 안되게 막기
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS // editText 설정 해제
            minValue = YEAR_MIN
            maxValue = YEAR_MAX
            value = today.year
        }

        val monthPicker = binding.npDatePickerMonth
        monthPicker.apply {
            wrapSelectorWheel = false
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            minValue = MONTH_MIN
            maxValue = MONTH_MAX
            value = today.monthValue
        }
        val dayPicker = binding.npDatePickerDay
        dayPicker.apply {
            wrapSelectorWheel = false
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            minValue = DAY_MIN
            maxValue = DAY_MAX
            value = today.dayOfMonth
        }
    }

    private fun addListeners() {
        binding.tvDatePickerDone.setOnClickListener {
            onDialogClosed(
                binding.npDatePickerYear.value,
                binding.npDatePickerMonth.value,
                binding.npDatePickerDay.value
            )
            dismiss()
        }
    }

    companion object {
        const val YEAR_MIN = 2024
        const val YEAR_MAX = 2050
        const val MONTH_MIN = 1
        const val MONTH_MAX = 12
        const val DAY_MIN = 1
        const val DAY_MAX = 31
    }
}
