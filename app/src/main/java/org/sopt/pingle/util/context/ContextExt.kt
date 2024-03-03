package org.sopt.pingle.util.context

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import org.sopt.pingle.R
import org.sopt.pingle.presentation.type.SnackbarType
import org.sopt.pingle.presentation.ui.common.WebViewActivity
import org.sopt.pingle.presentation.ui.common.WebViewActivity.Companion.WEB_VIEW_LINK
import org.sopt.pingle.presentation.ui.mygroup.MyGroupActivity
import org.sopt.pingle.util.component.PingleSnackbar

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

fun Context.sharePingle(shareContent: String) {
    Intent(Intent.ACTION_SEND_MULTIPLE).apply {
        type = SHARE_TYPE
        putExtra(Intent.EXTRA_TEXT, shareContent)
        startActivity(Intent.createChooser(this, null))
    }
}

fun Context.copyGroupCode(copyCode: String, view: View) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip: ClipData = ClipData.newPlainText(GROUP_CODE_COPY, copyCode)
    clipboard.setPrimaryClip(clip)

    PingleSnackbar.makeSnackbar(
        view = view,
        message = stringOf(R.string.my_group_snack_bar_code_copy_complete),
        bottomMarin = SNACKBAR_BOTTOM_MARGIN,
        snackbarType = SnackbarType.CHECK
    )
}

const val PINGLE_PLAY_STORE_LINK =
    "앱 링크 : https://play.google.com/store/apps/details?id=org.sopt.pingle&pcampaignid=web_share"
const val PINGLE_SHARE_CODE = "초대코드 : "
const val SHARE_TYPE = "text/plain"
const val GROUP_CODE_COPY = "CopyGroupCode"
const val SNACKBAR_BOTTOM_MARGIN = 57