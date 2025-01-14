package org.sopt.pingle.util.component

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import org.sopt.pingle.databinding.BadgePingleBinding
import org.sopt.pingle.presentation.type.CategoryType
import org.sopt.pingle.util.view.colorOf
import org.sopt.pingle.util.view.stringOf

@SuppressLint("CustomViewStyleable")
class PingleBadge @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: BadgePingleBinding

    init {
        binding = BadgePingleBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setBadgeCategoryType(categoryType: CategoryType) {
        with(binding.tvBadgePingleText) {
            text = stringOf(categoryType.categoryNameRes)
            setTextColor(colorOf(categoryType.textColor))
            backgroundTintList =
                ColorStateList.valueOf(colorOf(categoryType.backgroundBadgeColor))
        }
    }
}
