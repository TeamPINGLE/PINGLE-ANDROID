package org.sopt.pingle.presentation.type

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import org.sopt.pingle.R

enum class CategoryType(
    @ColorRes val textColor: Int,
    @ColorRes val activatedOutLinedColor: Int,
    @ColorRes val backgroundChipColor: Int,
    @ColorRes val backgroundBadgeColor: Int,
    @StringRes val categoryNameRes: Int
    // TODO 해당 부분은 UX, icon 정해지면 추가하기
    // @StringRes val categoryDescriptionRes: Int,
    // @DrawableRes val categoryIconRes: Int,
) {
    PLAY(
        R.color.pingle_green,
        R.color.pingle_green,
        R.color.chip_green,
        R.color.badge_green,
        R.string.category_play
    ),
    STUDY(
        R.color.pingle_orange,
        R.color.pingle_orange,
        R.color.chip_orange,
        R.color.badge_orange,
        R.string.category_study
    ),
    MULTI(
        R.color.pingle_yellow,
        R.color.pingle_yellow,
        R.color.chip_yellow,
        R.color.badge_yellow,
        R.string.category_multi
    ),
    OTHER(
        R.color.g_01,
        R.color.g_01,
        R.color.g_10,
        R.color.g_07,
        R.string.category_others
    )
}
