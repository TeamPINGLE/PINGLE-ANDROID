package org.sopt.pingle.data.datasource.remote

import org.sopt.pingle.util.base.NullableBaseResponse

interface PingleRemoteDataSource {
    suspend fun postPingleJoin(meetingId: Long): NullableBaseResponse<Unit?>
    suspend fun postPingleCancel(meetingId: Long): NullableBaseResponse<Unit?>
}