package org.sopt.pingle.data.model.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sopt.pingle.domain.model.PinEntity

@Serializable
data class ResponsePinListDto(
    @SerialName("id")
    val id: Long,
    @SerialName("x")
    val x: Double,
    @SerialName("y")
    val y: Double,
    @SerialName("category")
    val category: String
) {
    fun toPinEntity() = PinEntity(
        id = this.id,
        x = this.x,
        y = this.y,
        category = this.category
    )
}
