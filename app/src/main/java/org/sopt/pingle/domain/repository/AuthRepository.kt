package org.sopt.pingle.domain.repository

import org.sopt.pingle.data.model.remote.request.RequestAuthDto
import org.sopt.pingle.domain.model.AuthEntity

interface AuthRepository {
    suspend fun postLogin(kakaoAccessToken: String, requestAuthDto: RequestAuthDto): Result<AuthEntity>
}
