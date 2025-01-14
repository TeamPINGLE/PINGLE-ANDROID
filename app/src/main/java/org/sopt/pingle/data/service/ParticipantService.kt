package org.sopt.pingle.data.service

import org.sopt.pingle.data.model.remote.response.ResponseParticipantDto
import org.sopt.pingle.util.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ParticipantService {
    @GET("$VERSION/$MEETINGS/{$MEETING_ID}/$PARTICIPANTS")
    suspend fun getParticipantList(
        @Path("$MEETING_ID") meetingId: Long
    ): BaseResponse<ResponseParticipantDto>

    companion object {
        const val VERSION = "v1"
        const val MEETINGS = "meetings"
        const val MEETING_ID = "meetingId"
        const val PARTICIPANTS = "participants"
    }
}
