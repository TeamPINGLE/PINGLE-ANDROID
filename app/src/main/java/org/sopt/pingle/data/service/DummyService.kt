package org.sopt.pingle.data.service

import org.sopt.pingle.data.model.remote.response.ResponseGetDummyUserListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface DummyService {
    @GET("$API/$USERS")
    suspend fun getDummyListUserList(
        @Query("page") page: Int
    ): ResponseGetDummyUserListDto

    companion object {
        const val API = "api"
        const val USERS = "users"
    }
}
