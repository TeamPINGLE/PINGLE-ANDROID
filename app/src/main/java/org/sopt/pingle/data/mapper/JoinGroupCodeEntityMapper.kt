
package org.sopt.pingle.data.mapper

import org.sopt.pingle.data.model.remote.request.RequestJoinGroupCodeDto
import org.sopt.pingle.domain.model.JoinGroupCodeEntity

fun JoinGroupCodeEntity.toRequestJoinGroupCode() = RequestJoinGroupCodeDto(
    code = code
)
