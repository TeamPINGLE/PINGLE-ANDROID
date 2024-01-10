package org.sopt.pingle.data.model.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sopt.pingle.domain.model.AuthEntity

@Serializable
data class ResponseAuthDto(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String
) {
    fun toAuthModel() = AuthEntity(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken
    )
}
