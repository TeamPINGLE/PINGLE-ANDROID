package org.sopt.pingle.data.service

import org.sopt.pingle.data.model.remote.response.ResponseMainListDto
import org.sopt.pingle.util.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MainListService {
    @GET("$VERSION/$MEETINGS/$SEARCH")
    suspend fun getMainListPingleList(
        @Query(SEARCH_WORD) searchWord: String?,
        @Query(CATEGORY) category: String?,
        @Query(TEAM_ID) teamId: Long,
        @Query(ORDER) order: String
    ): BaseResponse<ResponseMainListDto>

    companion object {
        const val VERSION = "v1"
        const val MEETINGS = "meetings"
        const val SEARCH = "search"
        const val SEARCH_WORD = "q"
        const val CATEGORY = "category"
        const val TEAM_ID = "teamId"
        const val ORDER = "order"
    }
}
