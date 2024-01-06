package org.sopt.pingle.util.fragment

import android.content.Intent
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import org.sopt.pingle.presentation.ui.common.WebViewActivity

fun Fragment.showToast(message: String, isShort: Boolean = true) {
    val duration = if (isShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
    Toast.makeText(requireContext(), message, duration).show()
}

fun Fragment.stringOf(@StringRes resId: Int) = getString(resId)

fun Fragment.colorOf(@ColorRes resId: Int) = ContextCompat.getColor(requireContext(), resId)

fun Fragment.drawableOf(@DrawableRes resId: Int) =
    ContextCompat.getDrawable(requireContext(), resId)

fun Fragment.navigateToWebView(link: String) =
    Intent(requireContext(), WebViewActivity::class.java).apply {
        putExtra(WebViewActivity.WEB_VIEW_LINK, link)
    }
