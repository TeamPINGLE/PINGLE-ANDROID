package org.sopt.pingle.presentation.type

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.sopt.pingle.R

enum class GroupType(
    @StringRes val groupTextRes: Int,
    @DrawableRes val groupImageRes: Int
) {
    ORIGINAL(
        R.string.on_boarding_original_group,
        R.drawable.img_search_graphic
    ),
    NEW(
        R.string.on_boarding_new_group,
        R.drawable.img_create_graphic
    )
}
