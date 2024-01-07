package org.sopt.pingle.util.component

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import com.google.android.material.chip.Chip
import org.sopt.pingle.R
import org.sopt.pingle.presentation.type.CategoryType
import org.sopt.pingle.util.view.colorOf
import org.sopt.pingle.util.view.stringOf

@SuppressLint("CustomViewStyleable")
class PingleChip @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.style.Theme_Pingle_Chip_All,
) : Chip(context, attrs, defStyleAttr) {
    private lateinit var _categoryType: CategoryType
    val categoryType get() = _categoryType

    private fun setColorStateList(
        context: Context,
        activatedColorRes: Int,
        inactivatedColorRes: Int
    ) =
        ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked),
                intArrayOf(-android.R.attr.state_checked)
            ),
            intArrayOf(
                colorOf(activatedColorRes),
                colorOf(inactivatedColorRes)
            ),
        )

    fun setChipCategoryType(categoryType: CategoryType) {
        this@PingleChip._categoryType = categoryType

        val inactivatedOutlinedColor = R.color.g_09
        val inactivatedTextColor = R.color.g_03
        val inactivatedChipColor = R.color.g_11

        text = stringOf(categoryType.categoryNameRes)
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
            ),
        )
    }
}
