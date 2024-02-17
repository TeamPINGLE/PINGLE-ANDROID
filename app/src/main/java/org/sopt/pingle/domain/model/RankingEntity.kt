package org.sopt.pingle.domain.model

data class RankingEntity (
    val meetingCount: Int,
    val locations: List<RankingLocationEntity>
)
