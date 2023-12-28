package org.sopt.pingle.data.model.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sopt.pingle.domain.model.UserEntity

@Serializable
class ResponseGetDummyUserListDto(
    @SerialName("page")
    val page: Int,
    @SerialName("per_page")
    val perPage: Int,
    @SerialName("total")
    val total: Int,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("data")
    val data: List<User>,
    @SerialName("support")
    val support: Support
) {
    @Serializable
    data class User(
        @SerialName("id")
        val id: Int,
        @SerialName("email")
        val email: String,
        @SerialName("first_name")
        val firstName: String,
        @SerialName("last_name")
        val lastName: String,
        @SerialName("avatar")
        val avatar: String
    ) {
        fun toUserEntity() = UserEntity(
            id = this.id,
            firstName = this.firstName
        )
    }

    @Serializable
    data class Support(
        @SerialName("url")
        val url: String,
        @SerialName("text")
        val text: String
    )

    fun toUserEntityList() = data.map { user -> user.toUserEntity() }
}