package org.sopt.pingle.data.datasourceimpl.remote

import javax.inject.Inject
import org.sopt.pingle.data.datasource.remote.PingleRemoteDataSource
import org.sopt.pingle.data.model.remote.response.ResponseMyPingleDto
import org.sopt.pingle.data.service.PingleService
import org.sopt.pingle.util.base.BaseResponse
import org.sopt.pingle.util.base.NullableBaseResponse

class PingleRemoteDataSourceImpl @Inject constructor(
    private val pingleService: PingleService
) : PingleRemoteDataSource {
    override suspend fun postPingleJoin(meetingId: Long): NullableBaseResponse<Unit?> =
        pingleService.postPingleJoin(meetingId = meetingId)

    override suspend fun deletePingleCancel(meetingId: Long): NullableBaseResponse<Unit?> =
        pingleService.deletePingleCancel(meetingId = meetingId)

    override suspend fun getMyPingleList(
        teamId: Int,
        participation: Boolean
    ): BaseResponse<List<ResponseMyPingleDto>> =
        pingleService.getMyPingleList(teamId = teamId, participation = participation)

    override suspend fun deletePingle(meetingId: Long): NullableBaseResponse<Unit?> =
        pingleService.deletePingle(meetingId = meetingId)
}
