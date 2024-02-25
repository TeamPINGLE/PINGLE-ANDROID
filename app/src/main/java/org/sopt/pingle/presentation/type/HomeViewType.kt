package org.sopt.pingle.presentation.type

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.sopt.pingle.R

enum class HomeViewType(
    val index: Int,
    @DrawableRes val fabDrawableRes: Int,
    @StringRes val searchHintRes: Int,
    @StringRes val searchDescriptionRes: Int
) {
    MAP(
        index = 0,
        fabDrawableRes = R.drawable.ic_map_list_24,
        searchHintRes = R.string.home_view_map_search_hint,
        searchDescriptionRes = R.string.home_view_map_search_description
    ),
    MAIN_LIST(
        index = 1,
        fabDrawableRes = R.drawable.ic_map_map_24,
        searchHintRes = R.string.home_view_main_list_search_hint,
        searchDescriptionRes = R.string.home_view_main_list_search_description
    )
}
