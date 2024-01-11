package org.sopt.pingle.data.service

import org.sopt.pingle.data.model.remote.response.ResponseJoinGroupInfoDto
import org.sopt.pingle.util.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface JoinGroupService {
    @GET("$VERSION/$TEAMS/{$TEAM_ID}")
    suspend fun getJoinGroupDetail(
        @Path("$TEAM_ID") teamId: Int
    ): BaseResponse<ResponseJoinGroupInfoDto>

    companion object {
        const val VERSION = "v1"
        const val TEAMS = "teams"
        const val TEAM_ID = "teamId"
    }
}