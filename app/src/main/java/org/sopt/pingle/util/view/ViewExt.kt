package org.sopt.pingle.util.view

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getString
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.ViewCompat
import org.sopt.pingle.R
import org.sopt.pingle.presentation.type.SnackbarType
import org.sopt.pingle.util.component.PingleSnackbar
import org.sopt.pingle.util.context.GROUP_CODE_COPY
import org.sopt.pingle.util.context.SNACKBAR_BOTTOM_MARGIN
import org.sopt.pingle.util.context.stringOf

fun View.stringOf(@StringRes resId: Int) = getString(context, resId)

fun View.colorOf(@ColorRes resId: Int) = ContextCompat.getColor(context, resId)

fun View.setBackgroundTint(@ColorRes resId: Int) =
    ViewCompat.setBackgroundTintList(this, ContextCompat.getColorStateList(context, resId))

fun View.copyGroupCode(copyCode: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip: ClipData = ClipData.newPlainText(GROUP_CODE_COPY, copyCode)
    clipboard.setPrimaryClip(clip)

    PingleSnackbar.makeSnackbar(
        view = this,
        message = stringOf(R.string.my_group_snack_bar_code_copy_complete),
        bottomMarin = SNACKBAR_BOTTOM_MARGIN,
        snackbarType = SnackbarType.CHECK
    )
}
