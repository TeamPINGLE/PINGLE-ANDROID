package org.sopt.pingle.data.datasourceimpl.remote

import org.sopt.pingle.data.datasource.remote.PingleRemoteDataSource
import org.sopt.pingle.data.service.PingleService
import org.sopt.pingle.util.base.NullableBaseResponse
import javax.inject.Inject

class PingleRemoteDataSourceImpl @Inject constructor(
    private val pingleService: PingleService
) : PingleRemoteDataSource {
    override suspend fun postPingleJoin(meetingId: Long): NullableBaseResponse<Unit?> =
        pingleService.postPingleJoin(meetingId = meetingId)

    override suspend fun postPingleCancel(meetingId: Long): NullableBaseResponse<Unit?> =
        pingleService.postPingleCancel(meetingId = meetingId)
}
