package org.sopt.pingle.presentation.ui.main.planannouncement

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.TextAppearanceSpan
import androidx.core.content.ContextCompat
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityPlanAnnouncementBinding
import org.sopt.pingle.presentation.ui.plan.PlanActivity
import org.sopt.pingle.util.AmplitudeUtils
import org.sopt.pingle.util.base.BindingActivity

class PlanAnnouncementActivity :
    BindingActivity<ActivityPlanAnnouncementBinding>(R.layout.activity_plan_announcement) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        binding.tvPlanAnnouncementDetailTop.text = SpannableString(
            getString(
                R.string.plan_announcement_detail_top,
                PIN,
                MINGLE
            )
        ).apply {
            setSpan(
                TextAppearanceSpan(
                    this@PlanAnnouncementActivity,
                    R.style.TextAppearance_Pingle_Sub_Semi_16
                ),
                PIN_START,
                PIN.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        this@PlanAnnouncementActivity,
                        R.color.pingle_green
                    )
                ),
                PIN_START,
                PIN.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                TextAppearanceSpan(
                    this@PlanAnnouncementActivity,
                    R.style.TextAppearance_Pingle_Sub_Semi_16
                ),
                MINGLE_START,
                MINGLE_START + MINGLE.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        this@PlanAnnouncementActivity,
                        R.color.pingle_green
                    )
                ),
                MINGLE_START,
                MINGLE_START + MINGLE.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    private fun addListeners() {
        binding.ivPlanAnnouncementExitBtn.setOnClickListener {
            AmplitudeUtils.trackEvent(CLICK_MEETINGCANCEL)
            finish()
        }

        binding.btnPlanAnnouncement.setOnClickListener {
            AmplitudeUtils.trackEvent(START_MEETINGHOLD)
            navigateToPlan()
        }
    }

    private fun navigateToPlan() {
        Intent(this, PlanActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }

    companion object {
        const val PIN = "PIN"
        const val MINGLE = "MINGLE"
        const val PIN_START = 0
        const val MINGLE_START = 5
        const val START_MEETINGHOLD = "start_meetinghold"
        const val CLICK_MEETINGCANCEL = "click_meetingcancel"
    }
}
