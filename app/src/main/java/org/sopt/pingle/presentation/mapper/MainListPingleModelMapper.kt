package org.sopt.pingle.presentation.mapper

import org.sopt.pingle.domain.model.PingleEntity
import org.sopt.pingle.presentation.model.MainListPingleModel

fun PingleEntity.toMainListPingleModel(): MainListPingleModel =
    MainListPingleModel(pingleEntity = this)
