package org.sopt.pingle.data.service

import org.sopt.pingle.data.model.remote.response.ResponseNewGroupKeywordsDto
import org.sopt.pingle.util.base.BaseResponse
import retrofit2.http.GET

interface NewGroupService {
    @GET("$VERSION/$TEAMS/$KEYWORDS")
    suspend fun getNewGroupKeywords(): BaseResponse<List<ResponseNewGroupKeywordsDto>>

    companion object {
        const val VERSION = "v1"
        const val TEAMS = "teams"
        const val KEYWORDS = "keywords"
    }
}
