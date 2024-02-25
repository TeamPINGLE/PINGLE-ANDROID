package org.sopt.pingle.presentation.type

import androidx.annotation.DrawableRes
import org.sopt.pingle.R

enum class HomeViewType(val index: Int, @DrawableRes val fabDrawableRes: Int) {
    MAP(index = 0, fabDrawableRes = R.drawable.ic_map_list_24),
    MAIN_LIST(index = 1, fabDrawableRes = R.drawable.ic_map_map_24)
}
