package org.sopt.pingle.domain.usecase

import org.sopt.pingle.domain.model.NewGroupKeywordsEntity
import org.sopt.pingle.domain.repository.NewGroupRepository

class GetNewGroupKeywordsUserCase(
    private val newGroupRepository: NewGroupRepository
) {
    suspend operator fun invoke(): Result<List<NewGroupKeywordsEntity>> = newGroupRepository.getNewGroupKeywords()
}
