package org.sopt.pingle.data.service

import org.sopt.pingle.data.model.remote.response.ResponsePlanDto
import org.sopt.pingle.util.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PlanService {
    @GET("$VERSION/$LOCATION")
    suspend fun getPlanLocationList(
        @Query(SEARCH_WORD) searchWord: String
    ): BaseResponse<List<ResponsePlanDto>>

    companion object {
        const val VERSION = "v1"
        const val LOCATION = "location"
        const val SEARCH_WORD = "search"
    }
}
