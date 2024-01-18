package org.sopt.pingle.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.sopt.pingle.domain.model.GroupEntity
import org.sopt.pingle.domain.model.JoinGroupCodeEntity
import org.sopt.pingle.domain.repository.JoinGroupRepository

class PostJoinGroupCodeUseCase(
    private val joinGroupRepository: JoinGroupRepository
) {
    suspend operator fun invoke(
        teamId: Int,
        joinGroupCodeEntity: JoinGroupCodeEntity
    ): Flow<GroupEntity> =
        joinGroupRepository.postJoinGroupCode(
            teamId = teamId,
            joinGroupCodeEntity = joinGroupCodeEntity
        )
}
