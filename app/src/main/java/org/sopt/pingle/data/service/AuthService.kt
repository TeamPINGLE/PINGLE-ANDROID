package org.sopt.pingle.data.service

import org.sopt.pingle.data.model.remote.request.RequestAuthDto
import org.sopt.pingle.data.model.remote.response.ResponseAuthDto
import org.sopt.pingle.util.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("/v1/auth/login")
    suspend fun postLogin(
        @Header("X-Provider-Token") header: String,
        @Body body: RequestAuthDto
    ): BaseResponse<ResponseAuthDto>
}
