package org.sopt.pingle.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.sopt.pingle.domain.model.JoinGroupInfoEntity
import org.sopt.pingle.domain.repository.JoinGroupCodeRepository

class GetJoinGroupInfoUseCase(
    private val joinGroupCodeRepository: JoinGroupCodeRepository
) {
    operator fun invoke(teamId: Int): Flow<JoinGroupInfoEntity> =
        joinGroupCodeRepository.getJoinGroupInfo(teamId = teamId)
}