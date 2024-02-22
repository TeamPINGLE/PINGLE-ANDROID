package org.sopt.pingle.util.view

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object DateTimeUtils {
    private const val DATE_FORMAT = "yyyy년 M월 d일"
    private const val TIME_FORMAT = "HH:mm"
    private const val NEW_LINE = "\n"
    private const val TIME_SEPARATOR = " ~ "
    private const val WIDTH_BAR = " | "

    private fun convertToDateFormat(localDate: LocalDate): String = localDate.format(
        DateTimeFormatter.ofPattern(
            DATE_FORMAT
        )
    )

    private fun convertToTimeFormat(localTime: LocalTime): String = localTime.format(
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

    fun convertToCalenderDetail(date: String, startAt: String, endAt: String): String =
        buildString {
            append(convertToDateFormat(LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)))
            append(WIDTH_BAR)
            append(convertToTimeFormat(LocalTime.parse(startAt, DateTimeFormatter.ISO_LOCAL_TIME)))
            append(TIME_SEPARATOR)
            append(convertToTimeFormat(LocalTime.parse(endAt, DateTimeFormatter.ISO_LOCAL_TIME)))
        }
}
