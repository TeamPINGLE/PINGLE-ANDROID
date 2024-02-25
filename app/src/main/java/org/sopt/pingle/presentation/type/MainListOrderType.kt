package org.sopt.pingle.presentation.type

import androidx.annotation.StringRes
import org.sopt.pingle.R

enum class MainListOrderType(
    @StringRes val mainListOrderStringRes: Int
) {
    NEW(mainListOrderStringRes = R.string.main_list_order_new),
    UPCOMING(mainListOrderStringRes = R.string.main_list_order_upcoming)
}
