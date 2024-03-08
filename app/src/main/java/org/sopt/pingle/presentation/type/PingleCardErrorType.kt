package org.sopt.pingle.presentation.type

import androidx.annotation.StringRes
import org.sopt.pingle.R

enum class PingleCardErrorType(
    val code: Int,
    @StringRes val snackbarStringRes: Int
) {
    DELETED(
        code = 404,
        snackbarStringRes = R.string.pingle_card_error_deleted_snackbar
    ),
    COMPLETED(
        code = 409,
        snackbarStringRes = R.string.pingle_card_error_completed_snackbar
    )
}
