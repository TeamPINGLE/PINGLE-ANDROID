package org.sopt.pingle.data.model.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class ResponsePlanLocationDto(
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: List<Data>
) {
    @Serializable
    data class Data(
        @SerialName("x")
        val xCoordinate: Double,
        @SerialName("y")
        val yCoordinate: Double,
        @SerialName("location")
        val locationName: String,
        @SerialName("address")
        val locationAddress: String,
        @SerialName("roadAddress")
        val locationRoadAddress: String,
    )
}
