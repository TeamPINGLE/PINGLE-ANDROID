package org.sopt.pingle.data.datasourceimpl.remote

import javax.inject.Inject
import org.sopt.pingle.data.datasource.remote.PlanRemoteDataSource
import org.sopt.pingle.data.model.remote.request.RequestPlanMeetingDto
import org.sopt.pingle.data.model.remote.response.ResponsePlanDto
import org.sopt.pingle.data.service.PlanService
import org.sopt.pingle.util.base.BaseResponse
import org.sopt.pingle.util.base.NullableBaseResponse

class PlanRemoteDataSourceImpl @Inject constructor(
    private val planService: PlanService
) : PlanRemoteDataSource {
    override suspend fun getPlanLocation(searchWord: String): BaseResponse<List<ResponsePlanDto>> =
        planService.getPlanLocationList(searchWord = searchWord)

    override suspend fun postPlanMeeting(
        teamId: Int,
        requestPlanMeetingDto: RequestPlanMeetingDto
    ): NullableBaseResponse<Unit?> = planService.postPlanMeeting(teamId = teamId, request = requestPlanMeetingDto)
}
