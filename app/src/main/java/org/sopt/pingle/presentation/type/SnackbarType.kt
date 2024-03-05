package org.sopt.pingle.presentation.type

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import org.sopt.pingle.R

enum class SnackbarType(
    @ColorRes val textColorRes: Int,
    @ColorRes val backgroundColorRes: Int,
    @DrawableRes val snackbarIconRes: Int
) {
    WARNING(
        textColorRes = R.color.white,
        backgroundColorRes = R.color.alert_red,
        snackbarIconRes = R.drawable.ic_all_warning_notice_24
    ),
    GUIDE(
        textColorRes = R.color.black,
        backgroundColorRes = R.color.g_02,
        snackbarIconRes = R.drawable.ic_all_notice_24
    ),
    CHECK(
        textColorRes = R.color.black,
        backgroundColorRes = R.color.white,
        snackbarIconRes = R.drawable.ic_all_check_24
    )
}
