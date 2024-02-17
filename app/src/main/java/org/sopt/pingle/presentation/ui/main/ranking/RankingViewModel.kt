package org.sopt.pingle.presentation.ui.main.ranking

import androidx.lifecycle.ViewModel
import org.sopt.pingle.domain.model.RankingEntity
import org.sopt.pingle.domain.model.RankingLocationEntity

class RankingViewModel(): ViewModel() {
    val dummyRanking = RankingEntity(
        meetingCount = 30,
        locations = listOf(
            RankingLocationEntity(
                name = "솔트캐빈 화이트 용리단길점",
                latestVisitedDate = listOf(2024, 1, 31),
                locationCount = 20
            ),
            RankingLocationEntity(
                name = "하얀집 1호점",
                latestVisitedDate = listOf(2024, 1, 31),
                locationCount = 66
            ),
            RankingLocationEntity(
                name = "서울여자대학교 대학로캠퍼스",
                latestVisitedDate = listOf(2024, 2, 17),
                locationCount = 4
            ),
            RankingLocationEntity(
                name = "동국대학교 서울캠퍼스",
                latestVisitedDate = listOf(2024, 2, 1),
                locationCount = 100
            ),
            RankingLocationEntity(
                name = "국민대학교",
                latestVisitedDate = listOf(2023, 12, 30),
                locationCount = 13
            ),
            RankingLocationEntity(
                name = "IBK파이낸스타워",
                latestVisitedDate = listOf(2023, 11, 12),
                locationCount = 17
            ),
            RankingLocationEntity(
                name = "동탄시범다은마을월드메르디앙반도유보라아파트경비실",
                latestVisitedDate = listOf(2024, 1, 9),
                locationCount = 2
            )
        )
    )
}