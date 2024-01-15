package org.sopt.pingle.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.sopt.pingle.domain.model.MyPingleEntity
import org.sopt.pingle.domain.repository.PingleRepository

class GetPingleParticipationList(
    private val pingleRepository: PingleRepository
) {
    suspend operator fun invoke(
        teamId: Int,
        participation: Boolean
    ): Flow<List<MyPingleEntity>> =
        pingleRepository.getPingleParticipationList(teamId = teamId, participation = participation)
}
