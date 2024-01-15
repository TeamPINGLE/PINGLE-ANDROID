package org.sopt.pingle.util.component

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.snackbar.Snackbar
import org.sopt.pingle.databinding.ViewSnackbarBinding
import org.sopt.pingle.util.toPx

object PingleSnackbar {
    fun makeSnackbar(view: View, message: String, botMarin: Int) {
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
            bottomMargin = botMarin.toPx()
            marginStart = 16.toPx()
            marginEnd = 16.toPx()
        }

        with(snackbarLayout) {
            removeAllViews()
            setPadding(0, 0, 0, 0)
            addView(binding.root)
        }

        snackbar.show()
    }
}
