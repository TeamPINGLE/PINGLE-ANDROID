package org.sopt.pingle.data.service

import org.sopt.pingle.util.base.NullableBaseResponse
import retrofit2.http.POST
import retrofit2.http.Path

interface PingleService {
    @POST("$VERSION/$MEETINGS/{$MEETING_ID}/$JOIN")
    suspend fun postPingleParticipation(
        @Path("$MEETING_ID") meetingId: Long
    ): NullableBaseResponse<Unit?>

    companion object {
        const val VERSION = "v1"
        const val MEETINGS = "meetings"
        const val MEETING_ID = "meetingId"
        const val JOIN = "join"
    }
}