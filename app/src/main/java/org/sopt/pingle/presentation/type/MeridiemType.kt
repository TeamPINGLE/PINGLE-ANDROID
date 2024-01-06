package org.sopt.pingle.presentation.type

import androidx.annotation.StringRes
import org.sopt.pingle.R

enum class MeridiemType(
    @StringRes val meridiemStringRes: Int
) {
    AM(R.string.plan_time_am), PM(R.string.plan_time_pm)
}