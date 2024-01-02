package org.sopt.pingle.util.component

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import org.sopt.pingle.R
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

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.pingleBadge, defStyleAttr, DEF)

        initView(typedArray)
        typedArray.recycle()
    }

    private fun initView(typedArray: TypedArray) {
        typedArray.apply {
            with(binding.tvBadgePingleText) {
                text = typedArray.getText(R.styleable.pingleBadge_pingleBadgeCategoryName)
                setTextColor(typedArray.getColor(R.styleable.pingleBadge_pingleBadgeTextColor, DEF))
                backgroundTintList = ColorStateList.valueOf(
                    typedArray.getColor(
                        R.styleable.pingleBadge_pingleBadgeBackgroundColor,
                        DEF
                    )
                )
            }
        }
    }

    fun setBadgeCategoryType(categoryType: CategoryType) {
        with(binding.tvBadgePingleText) {
            text = context.getText(categoryType.categoryNameRes)
            setTextColor(context.getColor(categoryType.textColor))
            backgroundTintList =
                ColorStateList.valueOf(context.getColor(categoryType.backgroundBadgeColor))
        }
    }

    companion object {
        const val DEF = 0
    }
}
