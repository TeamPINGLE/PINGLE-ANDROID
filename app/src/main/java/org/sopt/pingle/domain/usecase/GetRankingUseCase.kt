package org.sopt.pingle.domain.usecase

import org.sopt.pingle.domain.model.RankingEntity
import org.sopt.pingle.domain.repository.RankingRepository

class GetRankingUseCase(
    private val rankingRepository: RankingRepository
) {
    suspend operator fun invoke(teamId: Long): Result<RankingEntity> =
        rankingRepository.getRanking(teamId = teamId)
}
