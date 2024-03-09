package org.sopt.pingle.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewGroupModel(
    val name: String,
    val code: String
) : Parcelable
