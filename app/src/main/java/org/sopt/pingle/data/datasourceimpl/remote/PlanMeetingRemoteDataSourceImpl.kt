package org.sopt.pingle.data.datasourceimpl.remote

import javax.inject.Inject
import org.sopt.pingle.data.datasource.remote.PlanMeetingRemoteDataSource
import org.sopt.pingle.data.model.remote.request.RequestPlanMeetingDto
import org.sopt.pingle.data.service.PlanService
import org.sopt.pingle.util.base.NullableBaseResponse

class PlanMeetingRemoteDataSourceImpl @Inject constructor(
    private val planService: PlanService
) : PlanMeetingRemoteDataSource {
    override suspend fun postPlanMeeting(
        teamId: Int,
        requestPlanMeetingDto: RequestPlanMeetingDto
    ): NullableBaseResponse<Unit?> = planService.postPlanMeeting(teamId = teamId, request = requestPlanMeetingDto)
}
