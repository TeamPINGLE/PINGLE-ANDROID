package org.sopt.pingle.data.service

import org.sopt.pingle.data.model.remote.response.ResponseMyGroupDto
import org.sopt.pingle.util.base.BaseResponse
import retrofit2.http.GET

interface MyGroupListService {
    @GET("$VERSION/$USERS/$ME/$TEAMS")
    suspend fun getMyGroupList(): BaseResponse<List<ResponseMyGroupDto>>

    companion object {
        const val VERSION = "v1"
        const val USERS = "users"
        const val ME = "me"
        const val TEAMS = "teams"
    }
}
