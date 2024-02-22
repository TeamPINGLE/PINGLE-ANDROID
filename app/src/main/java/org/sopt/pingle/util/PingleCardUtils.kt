package org.sopt.pingle.util

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import org.sopt.pingle.R
import org.sopt.pingle.domain.model.PingleEntity
import org.sopt.pingle.presentation.type.CategoryType
import org.sopt.pingle.presentation.ui.main.home.map.MapFragment
import org.sopt.pingle.presentation.ui.participant.ParticipantActivity
import org.sopt.pingle.util.component.AllModalDialogFragment
import org.sopt.pingle.util.component.PingleModalDialogFragment
import org.sopt.pingle.util.fragment.stringOf

object PingleCardUtils {
    private const val PINGLE_CANCEL_MODAL = "pingleCancelModal"
    private const val PINGLE_JOIN_MODAL = "pingleJoinModal"
    private const val PINGLE_DELETE_MODAL = "pingleDeleteModal"

    fun navigateToParticipant(context: Context, pingleEntityId: Long) {
        with(context) {
            Intent(this, ParticipantActivity::class.java).apply {
                putExtra(MapFragment.MEETING_ID, pingleEntityId)
                startActivity(this)
            }
        }
    }

    fun showPingleJoinModalDialogFragment(
        fragment: Fragment,
        postPingleJoin: () -> Unit,
        pingleEntity: PingleEntity
    ) {
        with(pingleEntity) {
            PingleModalDialogFragment(
                category = CategoryType.fromString(categoryName = category),
                name = name,
                ownerName = ownerName,
                clickBtn = { postPingleJoin() }
            ).show(fragment.childFragmentManager, PINGLE_JOIN_MODAL)
        }
    }


    fun showPingleCancelModalDialogFragment(
        fragment: Fragment,
        deletePingleCancel: () -> Unit
    ) {
        with(fragment) {
            AllModalDialogFragment(
                title = stringOf(R.string.cancel_modal_title),
                detail = stringOf(R.string.cancel_modal_detail),
                buttonText = stringOf(R.string.cancel_modal_button_text),
                textButtonText = stringOf(R.string.cancel_modal_text_button_text),
                clickBtn = { deletePingleCancel() },
                clickTextBtn = { }
            ).show(childFragmentManager, PINGLE_CANCEL_MODAL)
        }
    }

    fun showMapDeleteModalDialogFragment(
        fragment: Fragment,
        deletePingleDelete: () -> Unit
    ) {
        with(fragment) {
            AllModalDialogFragment(
                title = stringOf(R.string.delete_modal_title),
                detail = stringOf(R.string.delete_modal_detail),
                buttonText = stringOf(R.string.delete_modal_button_text),
                textButtonText = stringOf(R.string.delete_modal_text_button_text),
                clickBtn = { deletePingleDelete() },
                clickTextBtn = {}
            ).show(childFragmentManager, PINGLE_DELETE_MODAL)
        }
    }
}