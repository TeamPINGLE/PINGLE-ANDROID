package org.sopt.pingle.presentation.ui.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.databinding.ItemOnboardingExplanationDescriptionBinding
import org.sopt.pingle.databinding.ItemOnboardingExplanationTitleBinding
import org.sopt.pingle.presentation.type.OnboardingExplanationType
import org.sopt.pingle.util.view.ItemDiffCallback

class OnboardingExplanationAdapter(context: Context) :
    ListAdapter<String, RecyclerView.ViewHolder>(
        ItemDiffCallback<String>(
            onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old == new }
        )
    ) {
    private val inflater by lazy { LayoutInflater.from(context) }
    private var onBoardingExplanationList = OnboardingExplanationType.values()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TITLE_VIEW_TYPE -> OnboardingExplanationTitleViewHolder(
                ItemOnboardingExplanationTitleBinding.inflate(inflater, parent, false)
            )

            DESCRIPTION_VIEW_TYPE -> OnboardingExplanationDescriptionViewHolder(
                ItemOnboardingExplanationDescriptionBinding.inflate(inflater, parent, false)
            )

            else -> throw IllegalArgumentException("$VIEWTYPE_EXCEPTION_MESSAGE $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is OnboardingExplanationTitleViewHolder -> holder.onBind()
            is OnboardingExplanationDescriptionViewHolder -> {
                var decPosition = position - POSITION_MINUS
                holder.onBind(onBoardingExplanationList[decPosition])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == DEFAULT_POSITION) {
            TITLE_VIEW_TYPE
        } else {
            DESCRIPTION_VIEW_TYPE
        }
    }

    override fun getItemCount(): Int = ONBOARDING_SIZE

    companion object {
        const val DEFAULT_POSITION = 0
        const val TITLE_VIEW_TYPE = 1
        const val DESCRIPTION_VIEW_TYPE = 2
        const val ONBOARDING_SIZE = 4
        const val POSITION_MINUS = 1
        const val VIEWTYPE_EXCEPTION_MESSAGE = "Invalid viewType: "
    }
}
