package org.sopt.pingle.data.repository

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.sopt.pingle.data.datasource.remote.AuthRemoteDataSource
import org.sopt.pingle.data.model.remote.request.RequestAuthDto
import org.sopt.pingle.domain.model.AuthEntity
import org.sopt.pingle.domain.model.UserInfoEntity
import org.sopt.pingle.domain.repository.AuthRepository

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {
    override suspend fun postLogin(
        kakaoAccessToken: String,
        requestAuthDto: RequestAuthDto
    ): Result<AuthEntity> =
        runCatching {
            authRemoteDataSource.login(kakaoAccessToken, requestAuthDto).toAuthModel()
        }

    override suspend fun logout(): Result<Int> =
        runCatching {
            authRemoteDataSource.logout().code
        }

    override suspend fun withDraw(): Result<Int> =
        runCatching {
            authRemoteDataSource.withDraw().code
        }

    override suspend fun getUserInfo(): Flow<UserInfoEntity> = flow {
        val result = runCatching {
            authRemoteDataSource.getUserInfo().data.toUserInfoEntity()
        }
        emit(result.getOrThrow())
    }
}
