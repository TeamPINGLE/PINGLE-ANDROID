package org.sopt.pingle.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val id: Int,
    val firstName: String
) : Parcelable
