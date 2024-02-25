package org.sopt.pingle.presentation.type

import androidx.annotation.ColorRes
import org.sopt.pingle.R

enum class PingleCardType(
    @ColorRes val participationStatusColorRes: Int
) {
    MAP(
        participationStatusColorRes = R.color.g_10
    ),
    MAINLIST(
        participationStatusColorRes = R.color.g_09
    )
}
