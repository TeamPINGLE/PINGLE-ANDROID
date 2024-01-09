package org.sopt.pingle.presentation.ui.main.plan

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.TextAppearanceSpan
import androidx.core.content.ContextCompat
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityPlanAnnouncementBinding
import org.sopt.pingle.presentation.ui.main.MainActivity
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
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
            }
        }

        binding.btnPlanAnnouncement.setOnClickListener {
            Intent(this, PlanActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    companion object {
        const val PIN = "PIN"
        const val MINGLE = "MINGLE"
        const val PIN_START = 0
        const val MINGLE_START = 5
    }
}
