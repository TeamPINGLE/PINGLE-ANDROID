package org.sopt.pingle.presentation.ui.newgroup

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityNewGroupBinding
import org.sopt.pingle.presentation.ui.newgroup.newgroupannouncement.NewGroupAnnouncementActivity
import org.sopt.pingle.presentation.ui.newgroup.newgroupcreate.NewGroupCreateFragment
import org.sopt.pingle.presentation.ui.newgroup.newgroupinfo.NewGroupInfoActivity
import org.sopt.pingle.presentation.ui.newgroup.newgroupinput.NewGroupInputFragment
import org.sopt.pingle.presentation.ui.newgroup.newgroupkeyword.NewGroupKeywordFragment
import org.sopt.pingle.util.base.BindingActivity
import org.sopt.pingle.util.context.stringOf
import org.sopt.pingle.util.view.PingleFragmentStateAdapter

@AndroidEntryPoint
class NewGroupActivity : BindingActivity<ActivityNewGroupBinding>(R.layout.activity_new_group) {
    private val viewModel: NewGroupViewModel by viewModels()
    private lateinit var fragmentList: ArrayList<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        addListeners()
        collectData()
    }

    private fun initLayout() {
        navigateToNewGroupInfo()
        setPlanFragmentStateAdapter()
    }

    private fun addListeners() {
        binding.btnNewGroupNext.setOnClickListener {
            when (binding.vpNewGroup.currentItem) {
                fragmentList.size - LAST_INDEX_OFFSET -> {
                    navigateToNewGroupAnnouncement()
                    finish()
                }

                else -> binding.vpNewGroup.currentItem++
            }
        }

        binding.includeNewGroupTopbar.ivAllTopbarArrowWithTitleArrowLeft.setOnClickListener {
            navigateToPreviousPage()
        }

        binding.ivNewGroupTopbarInfo.setOnClickListener {
            navigateToNewGroupInfo()
        }
    }

    private fun collectData() {
        viewModel.currentPage.flowWithLifecycle(lifecycle).onEach { currentPage ->
            when (currentPage) {
                fragmentList.size - LAST_INDEX_OFFSET ->
                    binding.btnNewGroupNext.text =
                        stringOf(R.string.new_group_create)

                else -> binding.btnNewGroupNext.text = stringOf(R.string.new_group_next)
            }
        }.launchIn(lifecycleScope)
    }

    private fun setPlanFragmentStateAdapter() {
        fragmentList = ArrayList()
        fragmentList.apply {
            add(NewGroupInputFragment())
            add(NewGroupKeywordFragment())
            add(NewGroupCreateFragment())
        }

        val adapter = PingleFragmentStateAdapter(fragmentList, this)
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
            startActivity(
                this,
                ActivityOptions.makeCustomAnimation(this@NewGroupActivity, R.anim.slide_up, 0)
                    .toBundle()
            )
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
        const val LAST_INDEX_OFFSET = 1
    }
}
