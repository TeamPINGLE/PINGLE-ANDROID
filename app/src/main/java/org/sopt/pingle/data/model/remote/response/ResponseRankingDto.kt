package org.sopt.pingle.data.model.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sopt.pingle.domain.model.RankingEntity
import org.sopt.pingle.domain.model.RankingLocationEntity

@Serializable
data class ResponseRankingDto(
    @SerialName("meetingCount")
    val meetingCount: Int,
    @SerialName("locations")
    val locations: List<ResponseRankingLocationDto>
) {
    @Serializable
    data class ResponseRankingLocationDto(
        @SerialName("name")
        val name: String,
        @SerialName("latestVisitedDate")
        val latestVisitedDate: List<Int>,
        @SerialName("locationCount")
        val locationCount: Int
    ) {
        fun toRankingLocationEntity() = RankingLocationEntity(
            name = this.name,
            latestVisitedDate = this.latestVisitedDate.subList(DATE_START_INDEX, DATE_END_INDEX),
            locationCount = this.locationCount
        )
    }

    fun toRankingEntity() = RankingEntity(
        meetingCount = this.meetingCount,
        locations = this.locations.map { responseRankingLocationDto -> responseRankingLocationDto.toRankingLocationEntity() }
    )

    companion object {
        private const val DATE_START_INDEX = 0
        private const val DATE_END_INDEX = 3
    }
}
