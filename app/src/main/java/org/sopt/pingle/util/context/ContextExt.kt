package org.sopt.pingle.util.context

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import org.sopt.pingle.presentation.ui.common.WebViewActivity
import org.sopt.pingle.presentation.ui.common.WebViewActivity.Companion.WEB_VIEW_LINK

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    view.clearFocus()
}

fun Context.stringOf(@StringRes resId: Int) = getString(resId)

fun Context.colorOf(@ColorRes resId: Int) = ContextCompat.getColor(this, resId)

fun Context.navigateToWebView(link: String) = Intent(this, WebViewActivity::class.java).apply {
    putExtra(WEB_VIEW_LINK, link)
}
