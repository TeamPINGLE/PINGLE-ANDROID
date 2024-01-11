package org.sopt.pingle.data.datasourceimpl.remote

import javax.inject.Inject
import org.sopt.pingle.data.datasource.remote.AuthRemoteDataSource
import org.sopt.pingle.data.model.remote.request.RequestAuthDto
import org.sopt.pingle.data.model.remote.response.ResponseAuthDto
import org.sopt.pingle.data.service.AuthService

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService
) : AuthRemoteDataSource {
    override suspend fun login(
        kakaoAccessToken: String,
        requestAuthDto: RequestAuthDto
    ): ResponseAuthDto =
        authService.postLogin(kakaoAccessToken, requestAuthDto).data
}