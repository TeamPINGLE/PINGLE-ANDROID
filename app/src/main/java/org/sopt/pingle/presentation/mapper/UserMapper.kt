package org.sopt.pingle.presentation.mapper

import org.sopt.pingle.domain.model.UserEntity
import org.sopt.pingle.presentation.model.UserModel

fun UserEntity.toUserModel() = UserModel(
    id = this.id,
    firstName = this.firstName
)
