package org.sopt.pingle.util.component

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import org.sopt.pingle.R
import org.sopt.pingle.databinding.CardPingleBinding
import org.sopt.pingle.domain.model.PingleEntity
import org.sopt.pingle.presentation.type.CategoryType
import org.sopt.pingle.util.view.colorOf
import org.sopt.pingle.util.view.stringOf
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@SuppressLint("CustomViewStyleable")
class PingleCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: CardPingleBinding
    var listener: OnPingleCardClickListener? = null

    init {
        binding = CardPingleBinding.inflate(LayoutInflater.from(context), this, true)

        addListeners()
    }

    private fun addListeners() {
        binding.btnCardBottomMapChat.setOnClickListener {
            listener?.onPingleCardChatBtnClickListener()
        }

        binding.btnCardBottomMapParticipate.setOnClickListener {
            listener?.onPingleCardParticipateBtnClickListener()
        }
    }

    fun initLayout(pingleEntity: PingleEntity) {
        val category: CategoryType = CategoryType.fromString(pingleEntity.category)

        with(binding) {
            badgeCardTopInfo.setBadgeCategoryType(category)
            tvCardTopInfoName.text = pingleEntity.name
            tvCardTopInfoName.setTextColor(colorOf(category.textColor))
            tvCardTopInfoOwnerName.text = pingleEntity.ownerName
            tvCardBottomCalenderDetail.text = pingleEntity.convertToCalenderDetail()
            tvCardBottomMapDetail.text = pingleEntity.location
            btnCardBottomMapChat.isEnabled = pingleEntity.isParticipating
            btnCardBottomMapParticipate.text = when (pingleEntity.isParticipating) {
                true -> stringOf(R.string.map_card_cancel)
                false -> stringOf(R.string.map_card_participate)
            }
            btnCardBottomMapParticipate.isEnabled =
                pingleEntity.isParticipating || !pingleEntity.isCompleted()


            if (pingleEntity.isCompleted()) {
                tvCardTopInfoParticipationStatusSlash.visibility = View.INVISIBLE
                tvCardTopInfoParticipationStatusCurrentParticipants.visibility = View.INVISIBLE
                tvCardTopInfoParticipationStatusMaxParticipants.visibility = View.INVISIBLE
                tvCardTopInfoParticipationCompleted.visibility = View.VISIBLE
            } else {
                tvCardTopInfoParticipationStatusCurrentParticipants.text =
                    pingleEntity.curParticipants.toString()
                tvCardTopInfoParticipationStatusCurrentParticipants.setTextColor(colorOf(category.textColor))
                tvCardTopInfoParticipationStatusSlash.visibility = View.VISIBLE
                tvCardTopInfoParticipationStatusCurrentParticipants.visibility = View.VISIBLE
                tvCardTopInfoParticipationStatusMaxParticipants.visibility = View.VISIBLE
                tvCardTopInfoParticipationCompleted.visibility = View.INVISIBLE
                tvCardTopInfoParticipationStatusMaxParticipants.text =
                    pingleEntity.maxParticipants.toString()
            }
        }
    }
}

interface OnPingleCardClickListener {
    fun onPingleCardChatBtnClickListener()
    fun onPingleCardParticipateBtnClickListener()
}

fun PingleEntity.isCompleted() = maxParticipants == curParticipants

fun PingleEntity.convertToCalenderDetail(): String {
    val localDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
    val startTime = LocalTime.parse(startAt, DateTimeFormatter.ISO_LOCAL_TIME)
    val endTime = LocalTime.parse(endAt, DateTimeFormatter.ISO_LOCAL_TIME)

    return buildString {
        append("${localDate.year}년 ${localDate.monthValue}월 ${localDate.dayOfMonth}일\n")
        append("${startTime.format(DateTimeFormatter.ofPattern("HH:mm"))} ~ ")
        append("${endTime.format(DateTimeFormatter.ofPattern("HH:mm"))}")
    }
}
