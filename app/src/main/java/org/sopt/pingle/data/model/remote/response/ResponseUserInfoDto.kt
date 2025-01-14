package org.sopt.pingle.data.model.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sopt.pingle.domain.model.GroupEntity
import org.sopt.pingle.domain.model.UserInfoEntity

@Serializable
data class ResponseUserInfoDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("provider")
    val provider: String,
    @SerialName("email")
    val email: String,
    @SerialName("groups")
    val groups: List<Group>
) {
    @Serializable
    data class Group(
        @SerialName("id")
        val id: Int,
        @SerialName("name")
        val name: String
    ) {
        fun toGroupEntity() = GroupEntity(
            id = this.id,
            name = this.name
        )
    }

    fun toUserInfoEntity() = UserInfoEntity(
        id = this.id,
        name = this.name,
        provider = this.provider,
        email = this.email,
        groups = this.groups.map { it.toGroupEntity() }
    )
}
