package org.sopt.pingle.data.datasource.remote

import org.sopt.pingle.data.model.remote.request.RequestPlanMeetingDto
import org.sopt.pingle.util.base.NullableBaseResponse

interface PlanMeetingRemoteDataSource {
    suspend fun postPlanMeeting(teamId: Int, requestPlanMeetingDto: RequestPlanMeetingDto): NullableBaseResponse<Unit?>
}
