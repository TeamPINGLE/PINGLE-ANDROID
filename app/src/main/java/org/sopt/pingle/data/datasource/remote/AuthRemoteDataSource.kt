package org.sopt.pingle.data.datasource.remote

import org.sopt.pingle.data.model.remote.request.RequestAuthDto
import org.sopt.pingle.data.model.remote.response.ResponseAuthDto
import org.sopt.pingle.data.model.remote.response.ResponseUserInfoDto
import org.sopt.pingle.util.base.BaseResponse
import org.sopt.pingle.util.base.NullableBaseResponse

interface AuthRemoteDataSource {
    suspend fun login(kakaoAccessToken: String, requestAuthDto: RequestAuthDto): ResponseAuthDto

    suspend fun logout(): NullableBaseResponse<String>

    suspend fun withDraw(): NullableBaseResponse<String>

    suspend fun getUserInfo(): BaseResponse<ResponseUserInfoDto>
}
