package org.sopt.pingle.data.service

import org.sopt.pingle.data.model.remote.request.RequestJoinGroupCodeDto
import org.sopt.pingle.data.model.remote.response.ResponseJoinGroupCodeDto
import org.sopt.pingle.data.model.remote.response.ResponseJoinGroupInfoDto
import org.sopt.pingle.util.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface JoinGroupService {
    @GET("$VERSION/$TEAMS/{$TEAM_ID}")
    suspend fun getJoinGroupInfo(
        @Path("$TEAM_ID") teamId: Int
    ): BaseResponse<ResponseJoinGroupInfoDto>

    @POST("$VERSION/$TEAMS/{$TEAM_ID}/$REGISTER")
    suspend fun postJoinGroupCode(
        @Path("$TEAM_ID") teamId: Int,
        @Body code: RequestJoinGroupCodeDto
    ): BaseResponse<ResponseJoinGroupCodeDto>

    companion object {
        const val VERSION = "v1"
        const val TEAMS = "teams"
        const val TEAM_ID = "teamId"
        const val REGISTER = "register"
    }
}
