package org.sopt.pingle.data.model.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sopt.pingle.domain.model.PingleEntity

@Serializable
data class ResponsePingleListDto(
    @SerialName("id")
    val id: Long,
    @SerialName("category")
    val category: String,
    @SerialName("name")
    val name: String,
    @SerialName("ownerName")
    val ownerName: String,
    @SerialName("location")
    val location: String,
    @SerialName("date")
    val date: String,
    @SerialName("startAt")
    val startAt: String,
    @SerialName("endAt")
    val endAt: String,
    @SerialName("maxParticipants")
    val maxParticipants: Int,
    @SerialName("curParticipants")
    val curParticipants: Int,
    @SerialName("isParticipating")
    val isParticipating: Boolean,
    @SerialName("chatLink")
    val chatLink: String,
    @SerialName("isOwner")
    val isOwner: Boolean
) {
    fun toPingleEntity() = PingleEntity(
        id = this.id,
        category = this.category,
        name = this.name,
        ownerName = this.ownerName,
        location = this.location,
        date = this.date,
        startAt = this.startAt,
        endAt = this.endAt,
        maxParticipants = this.maxParticipants,
        curParticipants = this.curParticipants,
        isParticipating = this.isParticipating,
        chatLink = this.chatLink,
        isOwner = this.isOwner
    )
}
