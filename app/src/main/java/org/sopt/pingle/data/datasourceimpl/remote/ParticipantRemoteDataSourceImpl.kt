package org.sopt.pingle.data.datasourceimpl.remote

import org.sopt.pingle.data.datasource.remote.ParticipantRemoteDataSource
import org.sopt.pingle.data.model.remote.response.ResponseParticipantDto
import org.sopt.pingle.data.service.ParticipantService
import org.sopt.pingle.util.base.BaseResponse
import javax.inject.Inject

class ParticipantRemoteDataSourceImpl @Inject constructor(
    private val participantService: ParticipantService
) : ParticipantRemoteDataSource {
    override suspend fun getParticipantList(meetingId: Long): BaseResponse<ResponseParticipantDto> =
        participantService.getParticipantList(meetingId = meetingId)
}