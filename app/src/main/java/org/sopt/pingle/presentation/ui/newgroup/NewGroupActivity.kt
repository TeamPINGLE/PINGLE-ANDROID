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
import org.sopt.pingle.util.base.BindingActivity
import org.sopt.pingle.util.component.PingleSnackbar
import org.sopt.pingle.util.context.stringOf
import org.sopt.pingle.util.view.PingleFragmentStateAdapter
import org.sopt.pingle.util.view.UiState

@AndroidEntryPoint
class NewGroupActivity : BindingActivity<ActivityNewGroupBinding>(R.layout.activity_new_group) {
    private val newGroupViewModel by viewModels<NewGroupViewModel>()
    private lateinit var fragmentList: ArrayList<Fragment>
    private lateinit var onBackPressed: OnBackPressedCallback

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
        onBackPressedBtn()
    }

    private fun addListeners() {
        binding.btnNewGroupNext.setOnClickListener { replaceFragment() }

        binding.includeNewGroupTopbar.ivAllTopbarArrowWithTitleArrowLeft.setOnClickListener { navigateToPreviousPage() }

        binding.ivNewGroupTopbarInfo.setOnClickListener { navigateToNewGroupInfo() }
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
                    is UiState.Success -> navigateToNewGroupAnnouncement(
                        NewGroupModel(
                            name = newGroupCreateState.data.name,
                            code = newGroupCreateState.data.code
                        )
                    )

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
            NEW_GROUP_INPUT_FRAGMENT_INDEX -> {
                if (newGroupViewModel.isEmailValid()) {
                    binding.vpNewGroup.currentItem++
                } else {
                    PingleSnackbar.makeSnackbar(
                        binding.root,
                        stringOf(R.string.new_group_email_snackbar),
                        SNACKBAR_BOTTOM_MARGIN,
                        SnackbarType.WARNING
                    )
                }
            }

            fragmentList.size - LAST_INDEX_OFFSET -> newGroupViewModel.postNewGroupCreate()

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
            FIRST_PAGE -> finish()

            else -> binding.vpNewGroup.currentItem--
        }
    }

    private fun onBackPressedBtn() {
        onBackPressed = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateToPreviousPage()
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressed)
    }

    companion object {
        const val FIRST_PAGE = 0
        const val LAST_INDEX_OFFSET = 1
        const val NEW_GROUP_INPUT_FRAGMENT_INDEX = 0
        const val SNACKBAR_BOTTOM_MARGIN = 97
        const val NEW_GROUP_CODE = "NewGroupCode"
    }
}
