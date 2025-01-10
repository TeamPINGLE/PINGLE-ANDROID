package org.sopt.pingle.data.service

import org.sopt.pingle.data.model.remote.request.RequestAuthDto
import org.sopt.pingle.data.model.remote.response.ResponseAuthDto
import org.sopt.pingle.data.model.remote.response.ResponseUserInfoDto
import org.sopt.pingle.util.base.BaseResponse
import org.sopt.pingle.util.base.NullableBaseResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("$VERSION/$AUTH/$LOGIN")
    suspend fun postLogin(
        @Header(X_PROVIDER_TOKEN) header: String,
        @Body body: RequestAuthDto
    ): BaseResponse<ResponseAuthDto>

    @POST("$VERSION/$AUTH/$LOGOUT")
    suspend fun logout(): NullableBaseResponse<String>

    @DELETE("$VERSION/$USERS/$LEAVE")
    suspend fun withDraw(): NullableBaseResponse<String>

    @GET("$VERSION/$USERS/$ME")
    suspend fun getUserInfo(): BaseResponse<ResponseUserInfoDto>

    companion object {
        const val VERSION = "v1"
        const val AUTH = "auth"
        const val USERS = "users"
        const val LOGIN = "login"
        const val LOGOUT = "logout"
        const val LEAVE = "leave"
        const val X_PROVIDER_TOKEN = "X-Provider-Token"
        const val ME = "me"
    }
}
