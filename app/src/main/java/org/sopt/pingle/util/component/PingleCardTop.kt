package org.sopt.pingle.util.component

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import org.sopt.pingle.databinding.TopCardPingleBinding
import org.sopt.pingle.domain.model.PingleEntity
import org.sopt.pingle.presentation.mapper.isCompleted
import org.sopt.pingle.presentation.type.CategoryType
import org.sopt.pingle.util.view.colorOf

@SuppressLint("CustomViewStyleable")
class PingleCardTop @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: TopCardPingleBinding
    private var onParticipationStatusLayoutClick: (Long) -> Unit = {}

    init {
        binding = TopCardPingleBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun initLayout(pingleEntity: PingleEntity) {
        with(binding) {
            (CategoryType.fromString(pingleEntity.category)).let { category ->
                badgeCardTopInfo.setBadgeCategoryType(category)
                tvCardTopInfoParticipationStatusCurrentParticipants.setTextColor(colorOf(category.textColor))
                tvCardTopInfoName.setTextColor(colorOf(category.textColor))
            }
            tvCardTopInfoName.text = pingleEntity.name
            tvCardTopInfoOwnerName.text = pingleEntity.ownerName

            (pingleEntity.isCompleted()).let { isCompleted ->
                tvCardTopInfoParticipationStatusSlash.visibility =
                    if (isCompleted) View.INVISIBLE else View.VISIBLE
                tvCardTopInfoParticipationStatusCurrentParticipants.visibility =
                    if (isCompleted) View.INVISIBLE else View.VISIBLE
                tvCardTopInfoParticipationStatusMaxParticipants.visibility =
                    if (isCompleted) View.INVISIBLE else View.VISIBLE
                tvCardTopInfoParticipationCompleted.visibility =
                    if (isCompleted) View.VISIBLE else View.INVISIBLE

                if (!isCompleted) {
                    tvCardTopInfoParticipationStatusCurrentParticipants.text =
                        pingleEntity.curParticipants.toString()
                    tvCardTopInfoParticipationStatusMaxParticipants.text =
                        pingleEntity.maxParticipants.toString()
                }
            }

            layoutCardTopParticipationStatus.setOnClickListener {
                onParticipationStatusLayoutClick(pingleEntity.id)
            }
        }
    }

    fun setOnParticipationStatusLayoutClick(participationStatusLayoutClickListener: (Long) -> Unit) {
        onParticipationStatusLayoutClick = participationStatusLayoutClickListener
    }
}
