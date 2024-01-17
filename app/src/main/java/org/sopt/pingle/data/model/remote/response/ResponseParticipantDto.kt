package org.sopt.pingle.data.model.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sopt.pingle.domain.model.ParticipantEntity

@Serializable
data class ResponseParticipantDto(
    @SerialName("participants")
    val participants: List<String>
) {
    fun toParticipantEntity() = ParticipantEntity(
        participants = participants
    )
}
