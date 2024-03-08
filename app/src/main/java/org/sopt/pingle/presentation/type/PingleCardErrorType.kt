package org.sopt.pingle.presentation.type

import androidx.annotation.StringRes
import org.sopt.pingle.R

enum class PingleCardErrorType(
    val code: Int,
    @StringRes val message: Int,
    @StringRes val snackbarStringRes: Int
) {
    DELETED_PINGLE_AT_JOIN(
        code = 404,
        message = R.string.pingle_card_error_deleted_pingle_at_join_message,
        snackbarStringRes = R.string.pingle_card_error_delete_snackbar
    ),
    COMPLETED_PINGLE_AT_JOIN(
        code = 409,
        message = R.string.pingle_card_error_completed_pingle_at_join_message,
        snackbarStringRes = R.string.pingle_card_error_completed_snackbar
    ),
    DELETED_PINGLE_AT_CANCEL(
        code = 404,
        message = R.string.pingle_card_error_deleted_pingle_at_cancel_message,
        snackbarStringRes = R.string.pingle_card_error_delete_snackbar
    )
}