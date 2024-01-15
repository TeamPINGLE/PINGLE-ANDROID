package org.sopt.pingle.presentation.mapper

import org.sopt.pingle.domain.model.PingleEntity

fun PingleEntity.isCompleted() = maxParticipants == curParticipants
