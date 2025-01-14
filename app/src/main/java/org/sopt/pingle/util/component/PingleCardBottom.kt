package org.sopt.pingle.util.component

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import org.sopt.pingle.R
import org.sopt.pingle.databinding.BottomCardPingleBinding
import org.sopt.pingle.domain.model.PingleEntity
import org.sopt.pingle.presentation.mapper.isCompleted
import org.sopt.pingle.util.view.DateTimeUtils
import org.sopt.pingle.util.view.setOnSingleClickListener
import org.sopt.pingle.util.view.stringOf

@SuppressLint("CustomViewStyleable")
class PingleCardBottom @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: BottomCardPingleBinding
    private var onChatButtonClick: () -> Unit = {}
    private var onParticipateButtonClick: (Long?) -> Unit = {}
    private var _pinId: Long? = null
    val pinId get() = _pinId

    init {
        binding = BottomCardPingleBinding.inflate(LayoutInflater.from(context), this, true)

        addListeners()
    }

    private fun addListeners() {
        binding.btnCardBottomMapChat.setOnSingleClickListener {
            onChatButtonClick()
        }

        binding.btnCardBottomMapParticipate.setOnClickListener() {
            onParticipateButtonClick(pinId)
        }
    }

    fun initLayout(pingleEntity: PingleEntity) {
        with(binding) {
            tvCardBottomCalenderDetail.text = DateTimeUtils.convertToCalenderDetailWithNewLine(
                date = pingleEntity.date,
                startAt = pingleEntity.startAt,
                endAt = pingleEntity.endAt
            )
            tvCardBottomMapDetail.text = pingleEntity.location
            btnCardBottomMapChat.isEnabled = pingleEntity.isParticipating
            btnCardBottomMapParticipate.isEnabled = when {
                pingleEntity.isOwner -> true
                else -> pingleEntity.isParticipating || !pingleEntity.isCompleted()
            }

            btnCardBottomMapParticipate.text =
                when {
                    pingleEntity.isOwner -> stringOf(R.string.pingle_card_delete)
                    pingleEntity.isParticipating -> stringOf(R.string.pingle_card_cancel)
                    else -> stringOf(R.string.pingle_card_participate)
                }
        }
    }

    fun setPinId(pinId: Long) {
        _pinId = pinId
    }

    fun setOnChatButtonClick(chatButtonClickListener: () -> Unit) {
        onChatButtonClick = chatButtonClickListener
    }

    fun setOnParticipateButtonClick(participateButtonClickListener: (Long?) -> Unit) {
        onParticipateButtonClick = participateButtonClickListener
    }
}
