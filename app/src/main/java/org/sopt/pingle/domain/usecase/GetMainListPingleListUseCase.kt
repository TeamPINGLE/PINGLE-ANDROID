package org.sopt.pingle.domain.usecase

import org.sopt.pingle.domain.model.PingleEntity
import org.sopt.pingle.domain.repository.MainListRepository

class GetMainListPingleListUseCase(
    private val mainListRepository: MainListRepository
) {
    suspend operator fun invoke(
        searchWord: String?,
        category: String?,
        teamId: Long,
        order: String
    ): Result<List<PingleEntity>> = mainListRepository.getMainListPingleList(
        searchWord = searchWord,
        category = category,
        teamId = teamId,
        order = order
    )
}
