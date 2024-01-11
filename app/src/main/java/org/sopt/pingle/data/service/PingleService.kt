package org.sopt.pingle.data.service

import org.sopt.pingle.util.base.NullableBaseResponse
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface PingleService {
    @POST("$VERSION/$MEETINGS/{$MEETING_ID}/$JOIN")
    suspend fun postPingleJoin(
        @Path("$MEETING_ID") meetingId: Long
    ): NullableBaseResponse<Unit?>

    @DELETE("$VERSION/$MEETINGS/{$MEETING_ID}/$CANCEL")
    suspend fun postPingleCancel(
        @Path("$MEETING_ID") meetingId: Long
    ): NullableBaseResponse<Unit?>

    companion object {
        const val VERSION = "v1"
        const val MEETINGS = "meetings"
        const val MEETING_ID = "meetingId"
        const val JOIN = "join"
        const val CANCEL = "cancel"
    }
}