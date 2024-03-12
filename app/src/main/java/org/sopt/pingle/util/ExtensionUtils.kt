package org.sopt.pingle.util

import android.content.res.Resources

fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun String.makeEllipsisGroupName(): String = when {
    this.length > GROUP_NAME_MAX_LENGTH -> this.substring(SUBSTRING_START_INDEX, SUBSTRING_END_INDEX) + ELLIPSIS
    else -> this
}

private const val SUBSTRING_START_INDEX = 0
private const val SUBSTRING_END_INDEX = 12
private const val GROUP_NAME_MAX_LENGTH = 13
private const val ELLIPSIS = "..."