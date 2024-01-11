package org.sopt.pingle.data.repository

import javax.inject.Inject
import org.sopt.pingle.data.datasource.remote.AuthDataSource
import org.sopt.pingle.data.model.remote.request.RequestAuthDto
import org.sopt.pingle.domain.model.AuthEntity
import org.sopt.pingle.domain.repository.AuthRepository

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override suspend fun postLogin(
        kakaoAccessToken: String,
        requestAuthDto: RequestAuthDto
    ): Result<AuthEntity> =
        runCatching {
            authDataSource.login(kakaoAccessToken, requestAuthDto).toAuthModel()
        }
}
