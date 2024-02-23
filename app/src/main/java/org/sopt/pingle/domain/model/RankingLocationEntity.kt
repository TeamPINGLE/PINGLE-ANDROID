package org.sopt.pingle.domain.model

data class RankingLocationEntity(
    val name: String,
    val latestVisitedDate: List<Int>,
    val locationCount: Int
)
