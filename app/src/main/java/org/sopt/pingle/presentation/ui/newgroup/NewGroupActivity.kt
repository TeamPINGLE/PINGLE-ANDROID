package org.sopt.pingle.presentation.ui.newgroup

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
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
import org.sopt.pingle.presentation.model.NewGroupModel
import org.sopt.pingle.presentation.type.SnackbarType
import org.sopt.pingle.presentation.ui.newgroup.newgroupannouncement.NewGroupAnnouncementActivity
import org.sopt.pingle.presentation.ui.newgroup.newgroupcreate.NewGroupCreateFragment
import org.sopt.pingle.presentation.ui.newgroup.newgroupinfo.NewGroupInfoActivity
import org.sopt.pingle.presentation.ui.newgroup.newgroupinput.NewGroupInputFragment
import org.sopt.pingle.presentation.ui.newgroup.newgroupkeyword.NewGroupKeywordFragment
import org.sopt.pingle.util.AmplitudeUtils
import org.sopt.pingle.util.base.BindingActivity
import org.sopt.pingle.util.component.PingleSnackbar
import org.sopt.pingle.util.context.stringOf
import org.sopt.pingle.util.view.PingleFragmentStateAdapter
import org.sopt.pingle.util.view.UiState

@AndroidEntryPoint
class NewGroupActivity : BindingActivity<ActivityNewGroupBinding>(R.layout.activity_new_group) {
    private val newGroupViewModel by viewModels<NewGroupViewModel>()
    private lateinit var fragmentList: ArrayList<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.newGroupViewModel = newGroupViewModel

        initLayout()
        addListeners()
        collectData()
    }

    private fun initLayout() {
        navigateToNewGroupInfo()
        setPlanFragmentStateAdapter()
        onBackPressedCallBack()
    }

    private fun addListeners() {
        with(binding) {
            btnNewGroupNext.setOnClickListener { replaceFragment() }
            includeNewGroupTopbar.ivAllTopbarArrowWithTitleArrowLeft.setOnClickListener { navigateToPreviousPage() }
            ivNewGroupTopbarInfo.setOnClickListener {
                navigateToNewGroupInfo()
                trackEventInfo()
            }
        }
    }

    private fun collectData() {
        collectCurrentPage()
        collectNewGroupCreateState()
    }

    private fun collectCurrentPage() {
        newGroupViewModel.currentPage.flowWithLifecycle(lifecycle).onEach { currentPage ->
            when (currentPage) {
                fragmentList.size - LAST_INDEX_OFFSET ->
                    binding.btnNewGroupNext.text =
                        stringOf(R.string.new_group_create)

                else -> binding.btnNewGroupNext.text = stringOf(R.string.new_group_next)
            }
        }.launchIn(lifecycleScope)
    }

    private fun collectNewGroupCreateState() {
        newGroupViewModel.newGroupCreateState.flowWithLifecycle(lifecycle)
            .onEach { newGroupCreateState ->
                when (newGroupCreateState) {
                    is UiState.Success -> {
                        navigateToNewGroupAnnouncement(
                            NewGroupModel(
                                name = newGroupCreateState.data.name,
                                code = newGroupCreateState.data.code
                            )
                        )
                        AmplitudeUtils.trackEventWithProperties(
                            COMPLETE_CREATEGROUP,
                            mapOf(
                                GROUP_NAME to newGroupViewModel.newGroupName.value,
                                EMAIL to newGroupViewModel.newGroupEmail.value,
                                KEYWORD to newGroupViewModel.newGroupKeywordName.value
                            )
                        )
                    }

                    else -> {}
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
                        newGroupViewModel.setCurrentPage(position)
                    }
                })
        }
    }

    private fun replaceFragment() {
        when (binding.vpNewGroup.currentItem) {
            NEW_GROUP_INPUT_PAGE -> {
                if (newGroupViewModel.isEmailValid()) {
                    binding.vpNewGroup.currentItem++
                    AmplitudeUtils.trackEventWithProperty(CLICK_STEP1_NEXT, STATUS, STATUS_SUCCESS)
                } else {
                    PingleSnackbar.makeSnackbar(
                        binding.root,
                        stringOf(R.string.new_group_email_snackbar),
                        SNACKBAR_BOTTOM_MARGIN,
                        SnackbarType.WARNING
                    )
                    AmplitudeUtils.trackEventWithProperty(CLICK_STEP1_NEXT, STATUS, STATUS_FAILURE)
                }
            }

            fragmentList.size - LAST_INDEX_OFFSET -> {
                newGroupViewModel.postNewGroupCreate()
                AmplitudeUtils.trackEvent(CLICK_CREATEGROUP_MAKE)
            }

            else -> binding.vpNewGroup.currentItem++
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

    private fun navigateToNewGroupAnnouncement(newGroupModel: NewGroupModel) {
        Intent(this, NewGroupAnnouncementActivity::class.java).apply {
            putExtra(NEW_GROUP_CODE, newGroupModel)
            startActivity(this)
        }
        finish()
    }

    private fun navigateToPreviousPage() {
        when (binding.vpNewGroup.currentItem) {
            NEW_GROUP_INPUT_PAGE -> finish()

            else -> binding.vpNewGroup.currentItem--
        }
    }

    private fun onBackPressedCallBack() {
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navigateToPreviousPage()
                }
            }
        )
    }

    private fun trackEventInfo() {
        when (newGroupViewModel.currentPage.value) {
            NEW_GROUP_INPUT_PAGE -> AmplitudeUtils.trackEvent(CLICK_STEP1_INFO)
            NEW_GROUP_INFO_PAGE -> AmplitudeUtils.trackEvent(CLICK_STEP2_INFO)
            NEW_GROUP_CREATE_PAGE -> AmplitudeUtils.trackEvent(CLICK_STEP3_INFO)
        }
    }

    companion object {
        const val LAST_INDEX_OFFSET = 1
        const val SNACKBAR_BOTTOM_MARGIN = 97
        const val NEW_GROUP_CODE = "NewGroupCode"
        const val NEW_GROUP_INPUT_PAGE = 0
        const val NEW_GROUP_INFO_PAGE = 1
        const val NEW_GROUP_CREATE_PAGE = 2
        const val CLICK_STEP1_NEXT = "click_step1_next"
        const val STATUS = "status"
        const val STATUS_SUCCESS = "유효성검사성공"
        const val STATUS_FAILURE = "유효성검사실패"
        const val CLICK_STEP1_INFO = "click_step1_info"
        const val CLICK_STEP2_INFO = "click_step2_info"
        const val CLICK_STEP3_INFO = "click_step3_info"
        const val CLICK_CREATEGROUP_MAKE = "click_creategroup_make"
        const val COMPLETE_CREATEGROUP = "complete_creategroup"
        const val GROUP_NAME = "groupname"
        const val EMAIL = "email"
        const val KEYWORD = "keyword"
    }
}
