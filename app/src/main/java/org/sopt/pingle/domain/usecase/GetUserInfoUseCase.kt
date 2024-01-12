package org.sopt.pingle.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.sopt.pingle.domain.model.UserInfoEntity
import org.sopt.pingle.domain.repository.AuthRepository

class GetUserInfoUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Flow<UserInfoEntity> = authRepository.getUserInfo()
}
