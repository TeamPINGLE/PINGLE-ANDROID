package org.sopt.pingle.util

import android.content.res.Resources
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.flowOf

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

fun convertToCalenderDetailWithNewLine(date: String, startAt: String, endAt: String): String =
    buildString {
        append(convertToDateFormat(LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)))
        append(NEW_LINE)
        append(convertToTimeFormat(LocalTime.parse(startAt, DateTimeFormatter.ISO_LOCAL_TIME)))
        append(TIME_SEPARATOR)
        append(convertToTimeFormat(LocalTime.parse(endAt, DateTimeFormatter.ISO_LOCAL_TIME)))
    }

fun convertToCalenderDetail(date: String, startAt: String, endAt: String): String = buildString {
    append(convertToDateFormat(LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)))
    append(WIDTH_BAR)
    append(convertToTimeFormat(LocalTime.parse(startAt, DateTimeFormatter.ISO_LOCAL_TIME)))
    append(TIME_SEPARATOR)
    append(convertToTimeFormat(LocalTime.parse(endAt, DateTimeFormatter.ISO_LOCAL_TIME)))
}

const val DATE_FORMAT = "yyyy년 M월 d일"
const val TIME_FORMAT = "HH:mm"
const val NEW_LINE = "\n"
const val TIME_SEPARATOR = " ~ "
const val WIDTH_BAR = " | "
