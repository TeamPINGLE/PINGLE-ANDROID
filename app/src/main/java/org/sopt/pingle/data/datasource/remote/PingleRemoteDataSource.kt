package org.sopt.pingle.data.datasource.remote

import org.sopt.pingle.util.base.BaseResponse
import org.sopt.pingle.util.base.NullableBaseResponse

interface PingleRemoteDataSource {
    suspend fun postPingleParticipation(meetingId: Long): NullableBaseResponse<Unit?>
}
