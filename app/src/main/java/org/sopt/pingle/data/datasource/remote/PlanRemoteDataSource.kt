package org.sopt.pingle.data.datasource.remote

import org.sopt.pingle.data.model.remote.request.RequestPlanMeetingDto
import org.sopt.pingle.data.model.remote.response.ResponsePlanDto
import org.sopt.pingle.util.base.BaseResponse
import org.sopt.pingle.util.base.NullableBaseResponse

interface PlanRemoteDataSource {
    suspend fun getPlanLocation(searchWord: String): BaseResponse<List<ResponsePlanDto>>

    suspend fun postPlanMeeting(teamId: Int, requestPlanMeetingDto: RequestPlanMeetingDto): NullableBaseResponse<Unit?>
}
