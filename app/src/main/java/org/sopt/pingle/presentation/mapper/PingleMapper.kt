package org.sopt.pingle.presentation.mapper

import org.sopt.pingle.domain.model.PingleEntity
import org.sopt.pingle.util.convertToDateFormat
import org.sopt.pingle.util.convertToTimeFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun PingleEntity.isCompleted() = maxParticipants == curParticipants

fun PingleEntity.convertToCalenderDetail(): String = buildString {
    append(convertToDateFormat(LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)))
    append(NEW_LINE)
    append(convertToTimeFormat(LocalTime.parse(startAt, DateTimeFormatter.ISO_LOCAL_TIME)))
    append(TIME_SEPARATOR)
    append(convertToTimeFormat(LocalTime.parse(startAt, DateTimeFormatter.ISO_LOCAL_TIME)))
}

const val NEW_LINE = "\n"
const val TIME_SEPARATOR = " ~ "

