package org.sopt.pingle.data.mapper

import org.sopt.pingle.data.model.remote.request.RequestJoinGroupCodeDto
import org.sopt.pingle.domain.model.RequestJoinGroupCodeEntity

fun RequestJoinGroupCodeEntity.toRequestJoinGroupCode() = RequestJoinGroupCodeDto(
    code = code
)
