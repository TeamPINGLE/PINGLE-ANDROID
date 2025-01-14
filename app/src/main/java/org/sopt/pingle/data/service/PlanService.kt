package org.sopt.pingle.data.service

import org.sopt.pingle.data.model.remote.request.RequestPlanMeetingDto
import org.sopt.pingle.data.model.remote.response.ResponsePlanDto
import org.sopt.pingle.util.base.BaseResponse
import org.sopt.pingle.util.base.NullableBaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface PlanService {
    @GET("$VERSION/$LOCATION")
    suspend fun getPlanLocationList(
        @Query(SEARCH_WORD) searchWord: String
    ): BaseResponse<List<ResponsePlanDto>>

    @POST("$VERSION/$MEETINGS")
    suspend fun postPlanMeeting(
        @Header(X_TEAM_ID) teamId: Int,
        @Body request: RequestPlanMeetingDto
    ): NullableBaseResponse<Unit?>

    companion object {
        const val VERSION = "v1"
        const val LOCATION = "location"
        const val SEARCH_WORD = "search"
        const val MEETINGS = "meetings"
        const val X_TEAM_ID = "X-Team-Id"
    }
}
