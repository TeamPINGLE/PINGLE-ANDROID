package org.sopt.pingle.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.sopt.pingle.presentation.type.HomeViewType

@Parcelize
data class SearchModel(
    val homeViewType: HomeViewType,
    val searchWord: String?
) : Parcelable
