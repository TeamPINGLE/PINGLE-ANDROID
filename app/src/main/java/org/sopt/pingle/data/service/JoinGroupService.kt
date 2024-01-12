package org.sopt.pingle.data.service

import org.sopt.pingle.data.model.remote.request.RequestJoinGroupCodeDto
import org.sopt.pingle.data.model.remote.response.ResponseJoinGroupCodeDto
import org.sopt.pingle.data.model.remote.response.ResponseJoinGroupInfoDto
import org.sopt.pingle.data.model.remote.response.ResponseJoinGroupSearchDto
import org.sopt.pingle.util.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface JoinGroupService {
    @GET("$VERSION/$TEAMS?$NAME=$TEAM_NAME")
    suspend fun getJoinGroupSearch(
        @Query("$TEAM_NAME") teamName: String
    ): BaseResponse<List<ResponseJoinGroupSearchDto>>

    @GET("$VERSION/$TEAMS/{$TEAM_ID}")
    suspend fun getJoinGroupInfo(
        @Path("$TEAM_ID") teamId: Int
    ): BaseResponse<ResponseJoinGroupInfoDto>

    @POST("$VERSION/$TEAMS/{$TEAM_ID}/$REGISTER")
    suspend fun postJoinGroupCode(
        @Path("$TEAM_ID") teamId: Int,
        @Body requestJoinGroupCode: RequestJoinGroupCodeDto
    ): BaseResponse<ResponseJoinGroupCodeDto>

    companion object {
        const val VERSION = "v1"
        const val TEAMS = "teams"
        const val NAME = "name"
        const val TEAM_NAME = "teamName"
        const val TEAM_ID = "teamId"
        const val REGISTER = "register"
    }
}
