package org.sopt.pingle.data.model.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sopt.pingle.domain.model.MyPingleEntity

@Serializable
data class ResponseMyPingleDto(
    @SerialName("id")
    val id: Int,
    @SerialName("category")
    val category: String,
    @SerialName("name")
    val name: String,
    @SerialName("ownerName")
    val ownerName: String,
    @SerialName("location")
    val location: String,
    @SerialName("dDay")
    val dDay: String,
    @SerialName("date")
    val date: String,
    @SerialName("startAt")
    val startAt: String,
    @SerialName("endAt")
    val endAt: String,
    @SerialName("curParticipants")
    val curParticipants: Int,
    @SerialName("maxParticipants")
    val maxParticipants: Int,
    @SerialName("isOwner")
    val isOwner: Boolean
) {
    fun toMyPingleEntity() = MyPingleEntity(
        id = this.id,
        category = this.category,
        name = this.name,
        ownerName = this.ownerName,
        location = this.location,
        dDay = this.dDay,
        date = this.date,
        startAt = this.startAt,
        endAt = this.endAt,
        curParticipants = this.curParticipants,
        maxParticipants = this.maxParticipants,
        isOwner = this.isOwner
    )
}
