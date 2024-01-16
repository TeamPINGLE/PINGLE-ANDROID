package org.sopt.pingle.util.component

import android.content.res.ColorStateList
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat.getColor
import com.google.android.material.snackbar.Snackbar
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ViewSnackbarBinding
import org.sopt.pingle.presentation.type.SnackbarType
import org.sopt.pingle.util.view.toPx

object PingleSnackbar {
    private const val SNACKBAR_MARGIN = 16
    private const val SNACKBAR_PADDING = 0

    fun makeSnackbar(
        view: View,
        message: String,
        bottomMarin: Int,
        snackbarType: SnackbarType = SnackbarType.WARNING
    ) {
        val inflater = LayoutInflater.from(view.context)
        val binding = ViewSnackbarBinding.inflate(inflater, null, false)

        binding.tvSnackbar.text = message

        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        val snackbarLayout = snackbar.view as ViewGroup

        val layoutParams = snackbarLayout.layoutParams as FrameLayout.LayoutParams

        snackbarLayout.layoutParams = layoutParams.apply {
            width = ViewGroup.LayoutParams.MATCH_PARENT
            height = ViewGroup.LayoutParams.WRAP_CONTENT
            gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
            bottomMargin = bottomMarin.toPx()
            marginStart = SNACKBAR_MARGIN.toPx()
            marginEnd = SNACKBAR_MARGIN.toPx()
        }

        when (snackbarType) {
            SnackbarType.GUIDE -> {
                with(binding) {
                    layoutSnackbar.backgroundTintList =
                        ColorStateList.valueOf(getColor(view.context, R.color.g_02))
                    ivSnackbarNotice.setImageResource(R.drawable.ic_all_notice_24)
                    tvSnackbar.setTextColor(getColor(view.context, R.color.black))
                }
            }

            else -> Unit
        }

        with(snackbarLayout) {
            removeAllViews()
            setPadding(SNACKBAR_PADDING, SNACKBAR_PADDING, SNACKBAR_PADDING, SNACKBAR_PADDING)
            addView(binding.root)
        }

        snackbar.show()
    }
}
