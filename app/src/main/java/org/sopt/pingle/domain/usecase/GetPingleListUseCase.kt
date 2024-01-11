package org.sopt.pingle.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.sopt.pingle.domain.model.PingleEntity
import org.sopt.pingle.domain.repository.MapRepository

class GetPingleListUseCase(
    private val mapRepository: MapRepository
) {
    suspend operator fun invoke(teamId: Long, pinId: Long): Flow<List<PingleEntity>> =
        mapRepository.getPingleList(teamId = teamId, pinId = pinId)
}
