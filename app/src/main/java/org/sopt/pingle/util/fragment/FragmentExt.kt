package org.sopt.pingle.util.fragment

import android.content.Intent
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import org.sopt.pingle.R
import org.sopt.pingle.presentation.ui.common.WebViewActivity

fun Fragment.stringOf(@StringRes resId: Int) = getString(resId)

fun Fragment.colorOf(@ColorRes resId: Int) = ContextCompat.getColor(requireContext(), resId)

fun Fragment.navigateToWebView(link: String) =
    Intent(requireContext(), WebViewActivity::class.java).apply {
        putExtra(WebViewActivity.WEB_VIEW_LINK, link)
    }

inline fun <reified T : Fragment> Fragment.navigateToFragment() {
    parentFragmentManager.commit {
        replace<T>(R.id.fcv_main_all_navi, T::class.java.canonicalName)
    }
}
