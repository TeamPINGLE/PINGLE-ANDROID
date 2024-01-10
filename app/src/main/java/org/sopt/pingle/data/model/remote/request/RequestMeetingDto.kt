package org.sopt.pingle.data.model.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sopt.pingle.domain.model.PlanMeetingEntity

@Serializable
data class RequestMeetingDto(
    @SerialName("category")
    val category: String,
    @SerialName("name")
    val meetingName: String,
    @SerialName("startAt")
    val startTime: String,
    @SerialName("endAt")
    val endTime: String,
    @SerialName("x")
    val coordinateX: Double,
    @SerialName("y")
    val coordinateY: Double,
    @SerialName("address")
    val address: String,
    @SerialName("roadAddress")
    val roadAddress: String,
    @SerialName("location")
    val locationName: String,
    @SerialName("maxParticipants")
    val maxParticipants: Int,
    @SerialName("chatLink")
    val chatLink: String,
){
    fun toPlanMeetingEntity() = PlanMeetingEntity(
        category = this.category,
        name = this.meetingName,
        startAt = this.startTime,
        endAt = this.endTime,
        x = this.coordinateX,
        y = this.coordinateY,
        address = this.address,
        location = this.locationName,
        maxParticipants = this.maxParticipants,
        chatLink = this.chatLink,
    )
}
