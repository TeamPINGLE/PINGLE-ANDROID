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
        textColor = R.color.pingle_green,
        activatedOutLinedColor = R.color.pingle_green,
        backgroundChipColor = R.color.chip_green,
        backgroundBadgeColor = R.color.badge_green,
        categoryNameRes = R.string.category_play
    ),
    STUDY(
        textColor = R.color.pingle_orange,
        activatedOutLinedColor = R.color.pingle_orange,
        backgroundChipColor = R.color.chip_orange,
        backgroundBadgeColor = R.color.badge_orange,
        categoryNameRes = R.string.category_study
    ),
    MULTI(
        textColor = R.color.pingle_yellow,
        activatedOutLinedColor = R.color.pingle_yellow,
        backgroundChipColor = R.color.chip_yellow,
        backgroundBadgeColor = R.color.badge_yellow,
        categoryNameRes = R.string.category_multi
    ),
    OTHER(
        textColor = R.color.g_01,
        activatedOutLinedColor = R.color.g_01,
        backgroundChipColor = R.color.g_10,
        backgroundBadgeColor = R.color.g_07,
        categoryNameRes = R.string.category_others
    )
}
