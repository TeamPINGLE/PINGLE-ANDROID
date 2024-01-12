package org.sopt.pingle.presentation.ui.plan.plandatetime

import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import org.sopt.pingle.R
import org.sopt.pingle.databinding.DialogTimePickerBinding
import org.sopt.pingle.presentation.type.MeridiemType
import org.sopt.pingle.util.base.BindingBottomSheetDialogFragment

class PlanTimeDialogFragment(
    private val onDialogClosed: (meridiem: String, hour: Int, minute: Int) -> Unit
) :
    BindingBottomSheetDialogFragment<DialogTimePickerBinding>(R.layout.dialog_time_picker) {
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
        val meridiemPicker = binding.npTimePickerMeridiem
        meridiemPicker.apply {
            wrapSelectorWheel = false
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            minValue = 0
            maxValue = 1
            displayedValues =
                arrayOf(
                    getString(MeridiemType.AM.meridiemStringRes),
                    getString(MeridiemType.PM.meridiemStringRes)
                )
        }

        val hoursPicker = binding.npTimePickerHour
        hoursPicker.apply {
            wrapSelectorWheel = false
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            minValue = HOUR_MIN
            maxValue = HOUR_MAX
        }
        val minutesPicker = binding.npTimePickerMinute
        minutesPicker.apply {
            wrapSelectorWheel = false
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            minValue = MINUTE_MIN
            maxValue = MINUTE_MAX
            setFormatter { value -> String.format("%02d", value) }
        }
    }

    private fun addListeners() {
        binding.tvTimePickerDone.setOnClickListener {
            with(binding) {
                onDialogClosed(
                    if (npTimePickerMeridiem.value == 0) MeridiemType.AM.name else MeridiemType.PM.name,
                    npTimePickerHour.value,
                    npTimePickerMinute.value
                )
            }
            dismiss()
        }
    }

    companion object {
        const val HOUR_MIN = 1
        const val HOUR_MAX = 12
        const val MINUTE_MIN = 0
        const val MINUTE_MAX = 59
    }
}
