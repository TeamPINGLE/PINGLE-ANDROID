package org.sopt.pingle.data.model.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestPlanMeetingDto(
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
    val chatLink: String
)
