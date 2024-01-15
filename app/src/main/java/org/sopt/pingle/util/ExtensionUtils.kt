package org.sopt.pingle.util

import android.content.res.Resources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

inline fun <reified T> List<Flow<T>>.combineAll(): Flow<List<T>> {
    return when (size) {
        0 -> flowOf(emptyList())
        else -> combineTransform(this) { flows ->
            emit(flows.toList())
        }
    }
}

fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun convertToDateFormat(localDate: LocalDate): String = localDate.format(
    DateTimeFormatter.ofPattern(
        DATE_FORMAT
    )
)

fun convertToTimeFormat(localTime: LocalTime): String = localTime.format(
    DateTimeFormatter.ofPattern(TIME_FORMAT)
)

const val DATE_FORMAT = "yyyy년 M월 d일"
const val TIME_FORMAT = "HH:mm"
