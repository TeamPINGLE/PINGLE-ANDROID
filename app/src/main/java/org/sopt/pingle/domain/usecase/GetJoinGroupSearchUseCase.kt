package org.sopt.pingle.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.sopt.pingle.domain.model.JoinGroupSearchEntity
import org.sopt.pingle.domain.repository.JoinGroupRepository

class GetJoinGroupSearchUseCase(
    private val joinGroupRepository: JoinGroupRepository
) {
    suspend operator fun invoke(teamName: String): Flow<List<JoinGroupSearchEntity>> =
        joinGroupRepository.getJoinGroupSearch(teamName = teamName)
}
