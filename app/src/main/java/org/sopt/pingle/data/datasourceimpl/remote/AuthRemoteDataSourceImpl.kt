package org.sopt.pingle.data.datasourceimpl.remote

import javax.inject.Inject
import org.sopt.pingle.data.datasource.remote.AuthRemoteDataSource
import org.sopt.pingle.data.model.remote.request.RequestAuthDto
import org.sopt.pingle.data.model.remote.response.ResponseAuthDto
import org.sopt.pingle.data.model.remote.response.ResponseUserInfoDto
import org.sopt.pingle.data.service.AuthService
import org.sopt.pingle.util.base.BaseResponse
import org.sopt.pingle.util.base.NullableBaseResponse

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService
) : AuthRemoteDataSource {
    override suspend fun login(
        kakaoAccessToken: String,
        requestAuthDto: RequestAuthDto
    ): ResponseAuthDto =
        authService.postLogin(kakaoAccessToken, requestAuthDto).data

    override suspend fun logout(): NullableBaseResponse<String> =
        authService.logout()

    override suspend fun withDraw(): NullableBaseResponse<String> =
        authService.withDraw()

    override suspend fun getUserInfo(): BaseResponse<ResponseUserInfoDto> =
        authService.getUserInfo()
}
