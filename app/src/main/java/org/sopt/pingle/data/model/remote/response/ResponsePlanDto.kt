package org.sopt.pingle.data.model.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sopt.pingle.domain.model.PlanLocationEntity

@Serializable
data class ResponsePlanDto(
    @SerialName("x")
    val xCoordinate: Double,
    @SerialName("y")
    val yCoordinate: Double,
    @SerialName("location")
    val locationName: String,
    @SerialName("address")
    val locationAddress: String,
    @SerialName("roadAddress")
    val locationRoadAddress: String
) {
    fun toPlanLocationEntity() = PlanLocationEntity(
        location = locationName,
        address = locationAddress,
        x = xCoordinate,
        y = yCoordinate,
        roadAddress = locationRoadAddress
    )
}
