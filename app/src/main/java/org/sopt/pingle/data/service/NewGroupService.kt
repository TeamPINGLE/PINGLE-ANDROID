package org.sopt.pingle.data.service

import org.sopt.pingle.data.model.remote.request.RequestNewGroupCreateDto
import org.sopt.pingle.data.model.remote.response.ResponseNewGroupCheckNameDto
import org.sopt.pingle.data.model.remote.response.ResponseNewGroupCreateDto
import org.sopt.pingle.data.model.remote.response.ResponseNewGroupKeywordsDto
import org.sopt.pingle.util.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NewGroupService {
    @GET("$VERSION/$TEAMS/$CHECK_NAME")
    suspend fun getNewGroupCheckName(
        @Query(NAME) teamName: String
    ): BaseResponse<ResponseNewGroupCheckNameDto>

    @GET("$VERSION/$TEAMS/$KEYWORDS")
    suspend fun getNewGroupKeywords(): BaseResponse<List<ResponseNewGroupKeywordsDto>>

    @POST("$VERSION/$TEAMS")
    suspend fun postNewGroupCreate(
        @Body requestNewGroupCreateDto: RequestNewGroupCreateDto
    ): BaseResponse<ResponseNewGroupCreateDto>

    companion object {
        const val VERSION = "v1"
        const val TEAMS = "teams"
        const val CHECK_NAME = "check-name"
        const val NAME = "name"
        const val KEYWORDS = "keywords"
    }
}
