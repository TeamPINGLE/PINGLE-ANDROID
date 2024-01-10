package org.sopt.pingle.data.repository

import org.sopt.pingle.data.datasource.local.PingleDataSource
import org.sopt.pingle.data.datasource.remote.AuthDataSource
import org.sopt.pingle.data.model.remote.request.RequestAuthDto
import org.sopt.pingle.domain.model.AuthEntity
import org.sopt.pingle.domain.repository.AuthRepository
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val localStorage: PingleDataSource
) : AuthRepository {
    override suspend fun postLogin(
        kakaoAccessToken: String,
        requestAuthDto: RequestAuthDto
    ): Result<AuthEntity> =
        runCatching {
            authDataSource.login(kakaoAccessToken, requestAuthDto).toAuthModel()
        }.onSuccess {
            with(localStorage) {
                it?.let {
                    accessToken = it.accessToken
                    refreshToken = it.refreshToken
                }
            }
        }.onFailure {
            Timber.e(it.message)
        }
}
