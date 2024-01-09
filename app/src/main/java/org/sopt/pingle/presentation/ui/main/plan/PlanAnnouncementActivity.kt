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
import org.sopt.pingle.util.base.BindingActivity

class PlanAnnouncementActivity :
    BindingActivity<ActivityPlanAnnouncementBinding>(R.layout.activity_plan_announcement) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        val pin = "PIN"
        val mingle = "MINGLE"

        binding.tvPlanAnnouncementDetailTop.text = SpannableString(
            getString(
                R.string.plan_announcement_detail_top,
                pin,
                mingle
            )
        ).apply {
            setSpan(
                TextAppearanceSpan(
                    this@PlanAnnouncementActivity,
                    R.style.TextAppearance_Pingle_Sub_Semi_16
                ),
                PIN_START,
                pin.length,
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
                pin.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                TextAppearanceSpan(
                    this@PlanAnnouncementActivity,
                    R.style.TextAppearance_Pingle_Sub_Semi_16
                ),
                MINGLE_START,
                MINGLE_START + mingle.length,
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
                MINGLE_START + mingle.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    private fun addListeners() {
        // TODO x버튼 클릭 시 이전 액티비티로 이동 binding.ivPlanAnnouncementExitBtn.setOnClickListener { }
        binding.btnPlanAnnouncement.setOnClickListener {
            val intent = Intent(this, PlanActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        const val PIN_START = 0
        const val MINGLE_START = 5
    }
}
