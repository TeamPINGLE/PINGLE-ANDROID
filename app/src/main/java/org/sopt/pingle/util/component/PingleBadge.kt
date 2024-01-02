package org.sopt.pingle.util.component

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import org.sopt.pingle.databinding.BadgePingleBinding
import org.sopt.pingle.presentation.type.CategoryType

@SuppressLint("CustomViewStyleable")
class PingleBadge @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: BadgePingleBinding

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = BadgePingleBinding.inflate(inflater, this, true)
    }

    fun setBadgeCategoryType(categoryType: CategoryType) {
        with(binding.tvBadgePingleText) {
            text = context.getText(categoryType.categoryNameRes)
            setTextColor(context.getColor(categoryType.textColor))
            backgroundTintList =
                ColorStateList.valueOf(context.getColor(categoryType.backgroundBadgeColor))
        }
    }
}
