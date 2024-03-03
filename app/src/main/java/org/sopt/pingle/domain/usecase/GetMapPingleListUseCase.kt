package org.sopt.pingle.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.sopt.pingle.domain.model.PingleEntity
import org.sopt.pingle.domain.repository.MapRepository

class GetMapPingleListUseCase(
    private val mapRepository: MapRepository
) {
    suspend operator fun invoke(
        teamId: Long,
        pinId: Long,
        category: String?
    ): Flow<List<PingleEntity>> =
        mapRepository.getPingleList(teamId = teamId, pinId = pinId, category = category)
}
