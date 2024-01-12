package org.sopt.pingle.presentation.mapper

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import org.sopt.pingle.domain.model.PingleEntity

fun PingleEntity.isCompleted() = maxParticipants == curParticipants

fun PingleEntity.convertToCalenderDetail(): String {
    val localDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
    val startTime = LocalTime.parse(startAt, DateTimeFormatter.ISO_LOCAL_TIME)
    val endTime = LocalTime.parse(endAt, DateTimeFormatter.ISO_LOCAL_TIME)

    return buildString {
        append("${localDate.year}년 ${localDate.monthValue}월 ${localDate.dayOfMonth}일\n")
        append("${startTime.format(DateTimeFormatter.ofPattern("HH:mm"))} ~ ")
        append("${endTime.format(DateTimeFormatter.ofPattern("HH:mm"))}")
    }
}
