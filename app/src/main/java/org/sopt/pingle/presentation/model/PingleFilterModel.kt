package org.sopt.pingle.presentation.model

import org.sopt.pingle.presentation.type.CategoryType
import org.sopt.pingle.presentation.type.HomeViewType
import org.sopt.pingle.presentation.type.MainListOrderType

data class PingleFilterModel (
    val category: CategoryType? = null,
    val searchWord: String? = null,
    val mainListOrderType: MainListOrderType = MainListOrderType.NEW,
    val homeViewType: HomeViewType = HomeViewType.MAP
)