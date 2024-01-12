package org.sopt.pingle.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.sopt.pingle.domain.model.RequestJoinGroupCodeEntity
import org.sopt.pingle.domain.model.ResponseJoinGroupCodeEntity
import org.sopt.pingle.domain.repository.JoinGroupRepository

class PostJoinGroupCodeUseCase(
    private val joinGroupRepository: JoinGroupRepository
) {
    operator fun invoke(
        teamId: Int,
        requestJoinGroupCode: RequestJoinGroupCodeEntity
    ): Flow<ResponseJoinGroupCodeEntity> =
        joinGroupRepository.postJoinGroupCode(
            teamId = teamId,
            requestJoinGroupCode = requestJoinGroupCode
        )
}
