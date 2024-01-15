package org.sopt.pingle.presentation.mapper

import org.sopt.pingle.domain.model.PingleEntity
import org.sopt.pingle.util.convertToDateFormat
import org.sopt.pingle.util.convertToTimeFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun PingleEntity.isCompleted() = maxParticipants == curParticipants
