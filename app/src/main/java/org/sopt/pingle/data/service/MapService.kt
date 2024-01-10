package org.sopt.pingle.data.service

import org.sopt.pingle.data.model.remote.response.ResponsePinListDto
import org.sopt.pingle.util.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MapService {
    @GET("$VERSION/$TEAMS/{$TEAM_ID}/$PINS")
    suspend fun getPinListWithoutFiltering(
        @Path("$TEAM_ID") teamId: Long,
        @Query(CATEGORY) category: String?
    ): BaseResponse<List<ResponsePinListDto>>

    companion object {
        const val VERSION = "v1"
        const val TEAMS = "teams"
        const val PINS = "pins"
        const val TEAM_ID = "teamId"
        const val CATEGORY = "category"
    }
}
