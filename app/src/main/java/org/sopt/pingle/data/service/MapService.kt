package org.sopt.pingle.data.service

import org.sopt.pingle.data.model.remote.response.ResponsePinDto
import org.sopt.pingle.data.model.remote.response.ResponsePingleDto
import org.sopt.pingle.util.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MapService {
    @GET("$VERSION/$TEAMS/{$TEAM_ID}/$PINS")
    suspend fun getPinListWithoutFiltering(
        @Path("$TEAM_ID") teamId: Long,
        @Query(CATEGORY) category: String?,
        @Query(SEARCH_WORD) searchWord: String?
    ): BaseResponse<List<ResponsePinDto>>

    @GET("$VERSION/$TEAMS/{$TEAM_ID}/$PINS/{$PIN_ID}/$MEETINGS")
    suspend fun getMapPingleList(
        @Path("$TEAM_ID") teamId: Long,
        @Path("$PIN_ID") pinId: Long,
        @Query(CATEGORY) category: String?
    ): BaseResponse<List<ResponsePingleDto>>

    companion object {
        const val VERSION = "v1"
        const val TEAMS = "teams"
        const val TEAM_ID = "teamId"
        const val PINS = "pins"
        const val PIN_ID = "pinId"
        const val CATEGORY = "category"
        const val SEARCH_WORD = "q"
        const val MEETINGS = "meetings"
    }
}
