package org.sopt.pingle.domain.usecase

import org.sopt.pingle.domain.model.NewGroupCheckNameEntity
import org.sopt.pingle.domain.repository.NewGroupRepository

class GetNewGroupCheckNameUseCase(
    private val newGroupRepository: NewGroupRepository
) {
    suspend operator fun invoke(teamName: String): Result<NewGroupCheckNameEntity> =
        newGroupRepository.getNewGroupCheckName(teamName = teamName)
}
