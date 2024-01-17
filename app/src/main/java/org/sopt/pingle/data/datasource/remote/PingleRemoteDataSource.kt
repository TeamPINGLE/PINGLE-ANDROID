package org.sopt.pingle.data.datasource.remote

import org.sopt.pingle.data.model.remote.response.ResponseMyPingleDto
import org.sopt.pingle.util.base.BaseResponse
import org.sopt.pingle.util.base.NullableBaseResponse

interface PingleRemoteDataSource {
    suspend fun postPingleJoin(meetingId: Long): NullableBaseResponse<Unit?>
    suspend fun deletePingleCancel(meetingId: Long): NullableBaseResponse<Unit?>
    suspend fun getPingleParticipationList(
        teamId: Int,
        participation: Boolean
    ): BaseResponse<List<ResponseMyPingleDto>>

    suspend fun deletePingle(meetingId: Long): NullableBaseResponse<Unit?>
}
