package org.sopt.pingle.presentation.ui.newgroup

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityNewGroupBinding
import org.sopt.pingle.util.base.BindingActivity

@AndroidEntryPoint
class NewGroupActivity : BindingActivity<ActivityNewGroupBinding>(R.layout.activity_new_group) {
    private val viewModel: NewGroupViewModel by viewModels()
    private lateinit var fragmentList: ArrayList<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        setPlanFragmentStateAdapter()
    }

    private fun addListeners() {
        binding.btnNewGroupNext.setOnClickListener {
            when (binding.vpNewGroup.currentItem) {
                fragmentList.size - SUB_LIST_SIZE -> {
                    // TODO 서버통신
                    navigateToNewGroupAnnouncement()
                }

                else -> {
                    binding.vpNewGroup.currentItem++
                }
            }
        }

        binding.includeNewGroupTopbar.ivAllTopbarArrowWithTitleArrowLeft.setOnClickListener {
            navigateToPreviousPage()
        }

        binding.includeNewGroupTopbar.ivAllTopbarArrowWithTitleInfo.setOnClickListener {
            navigateToNewGroupInfo()
        }
    }

    private fun setPlanFragmentStateAdapter() {
        fragmentList = ArrayList()
        fragmentList.apply {
            add(NewGroupInputFragment())
            add(NewGroupKeywordFragment())
            add(NewGroupCreateFragment())
        }

        val adapter = NewGroupFragmentStateAdapter(fragmentList, this)
        with(binding.vpNewGroup) {
            this.adapter = adapter
            isUserInputEnabled = false

            registerOnPageChangeCallback(object :
                    ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        viewModel.setCurrentPage(position)
                    }
                })
        }
    }

    private fun navigateToNewGroupInfo() {
        Intent(this, NewGroupInfoActivity::class.java).apply {
            putExtra("ActivityName", "NewGroupActivity")
            startActivity(this)
        }
    }

    private fun navigateToNewGroupAnnouncement() {
        Intent(this, NewGroupAnnouncementActivity::class.java).apply {
            startActivity(this)
        }
    }

    private fun navigateToPreviousPage() {
        when (binding.vpNewGroup.currentItem) {
            FIRST_PAGE -> finish()
            else -> binding.vpNewGroup.currentItem--
        }
    }

    companion object {
        const val FIRST_PAGE = 0
        const val SUB_LIST_SIZE = 1
    }
}
