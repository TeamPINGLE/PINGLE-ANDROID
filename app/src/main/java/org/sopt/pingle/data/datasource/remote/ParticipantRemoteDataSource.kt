package org.sopt.pingle.data.datasource.remote

import org.sopt.pingle.data.model.remote.response.ResponseParticipantDto
import org.sopt.pingle.util.base.BaseResponse

interface ParticipantRemoteDataSource {
    suspend fun getParticipantList(meetingId: Long): BaseResponse<ResponseParticipantDto>
}