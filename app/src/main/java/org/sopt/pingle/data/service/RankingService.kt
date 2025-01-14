package org.sopt.pingle.data.service

import org.sopt.pingle.data.model.remote.response.ResponseRankingDto
import org.sopt.pingle.util.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RankingService {
    @GET("$VERSION/$TEAMS/{$TEAM_ID}/$PINS/$RANKING")
    suspend fun getRanking(
        @Path("$TEAM_ID") teamId: Long
    ): BaseResponse<ResponseRankingDto>

    companion object {
        const val VERSION = "v1"
        const val TEAMS = "teams"
        const val TEAM_ID = "teamId"
        const val PINS = "pins"
        const val RANKING = "ranking"
    }
}
