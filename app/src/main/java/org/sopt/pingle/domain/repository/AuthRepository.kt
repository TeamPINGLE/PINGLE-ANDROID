package org.sopt.pingle.domain.repository

import kotlinx.coroutines.flow.Flow
import org.sopt.pingle.data.model.remote.request.RequestAuthDto
import org.sopt.pingle.domain.model.AuthEntity
import org.sopt.pingle.domain.model.UserInfoEntity

interface AuthRepository {
    suspend fun postLogin(
        kakaoAccessToken: String,
        requestAuthDto: RequestAuthDto
    ): Result<AuthEntity>

    suspend fun logout(): Result<Int>

    suspend fun withDraw(): Result<Int>

    suspend fun getUserInfo(): Flow<UserInfoEntity>
}
