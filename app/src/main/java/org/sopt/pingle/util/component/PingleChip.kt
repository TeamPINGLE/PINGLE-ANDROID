package org.sopt.pingle.util.component

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ChipPingleBinding
import org.sopt.pingle.presentation.type.CategoryType

@SuppressLint("CustomViewStyleable")
class PingleChip @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: ChipPingleBinding

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ChipPingleBinding.inflate(inflater, this, true)
    }

    private fun setColorStateList(
        context: Context,
        activatedColorRes: Int,
        inactivatedColorRes: Int
    ) =
        ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_selected),
                intArrayOf(-android.R.attr.state_selected)
            ),
            intArrayOf(
                ContextCompat.getColor(context, activatedColorRes),
                ContextCompat.getColor(context, inactivatedColorRes)
            )
        )

    fun setChipCategoryType(categoryType: CategoryType) {
        val inactivatedOutlinedColor = R.color.g_03
        val inactivatedTextColor = R.color.g_03
        val inactivatedChipColor = R.color.g_11

        with(binding.chipChipPingle) {
            text = context.getText(categoryType.categoryNameRes)
            chipStrokeColor = setColorStateList(
                context = context,
                activatedColorRes = categoryType.activatedOutLinedColor,
                inactivatedColorRes = inactivatedOutlinedColor
            )
            chipBackgroundColor = setColorStateList(
                context = context,
                activatedColorRes = categoryType.backgroundChipColor,
                inactivatedColorRes = inactivatedChipColor
            )
            setTextColor(
                setColorStateList(
                    context = context,
                    activatedColorRes = categoryType.textColor,
                    inactivatedColorRes = inactivatedTextColor
                )
            )
        }
    }
}
