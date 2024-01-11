package org.sopt.pingle.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.sopt.pingle.domain.model.JoinGroupInfoEntity
import org.sopt.pingle.domain.repository.JoinGroupRepository

class GetJoinGroupInfoUseCase(
    private val joinGroupRepository: JoinGroupRepository
) {
    operator fun invoke(teamId: Int): Flow<JoinGroupInfoEntity> =
        joinGroupRepository.getJoinGroupInfo(teamId = teamId)
}