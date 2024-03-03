package org.sopt.pingle.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.sopt.pingle.domain.model.PinEntity
import org.sopt.pingle.domain.repository.MapRepository

class GetPinListWithoutFilteringUseCase(
    private val mapRepository: MapRepository
) {
    suspend operator fun invoke(
        teamId: Long,
        category: String?,
        searchWord: String?
    ): Flow<List<PinEntity>> =
        mapRepository.getPinListWithoutFiltering(
            teamId = teamId,
            category = category,
            searchWord = searchWord
        )
}
