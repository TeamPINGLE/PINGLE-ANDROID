package org.sopt.pingle.presentation.ui.mygroup

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityMyGroupBinding
import org.sopt.pingle.domain.model.MyGroupEntity
import org.sopt.pingle.presentation.type.SnackbarType
import org.sopt.pingle.presentation.ui.onboarding.onboarding.OnboardingActivity
import org.sopt.pingle.util.base.BindingActivity
import org.sopt.pingle.util.component.PingleSnackbar
import org.sopt.pingle.util.context.PINGLE_PLAY_STORE_LINK
import org.sopt.pingle.util.context.sharePingle
import org.sopt.pingle.util.context.stringOf
import org.sopt.pingle.util.view.UiState
import org.sopt.pingle.util.view.copyGroupCode
import timber.log.Timber

@AndroidEntryPoint
class MyGroupActivity : BindingActivity<ActivityMyGroupBinding>(R.layout.activity_my_group) {

    private val viewModel by viewModels<MyGroupViewModel>()
    private lateinit var adapter: MyGroupAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.myGroupviewModel = viewModel

        initLayout()
        addListeners()
        collectData()
    }

    override fun onDestroy() {
        binding.rvMyGroupSelected.adapter = null
        super.onDestroy()
    }

    private fun initLayout() {
        binding.toolbarMyGroup.text = stringOf(R.string.my_group_title)
        viewModel.getGroupList()

        runCatching {
            adapter = MyGroupAdapter(this@MyGroupActivity::showChangeGroupModal)
            binding.rvMyGroupSelected.adapter = adapter
        }.onFailure { throwable -> Timber.e(throwable.message) }
    }

    private fun addListeners() {
        with(binding) {
            toolbarMyGroup.ivAllTopbarArrowWithTitleArrowLeft.setOnClickListener { finish() }
            tvMyGroupMoveSelectedGroup.setOnClickListener { navigateToNewGroupInfo() }
            ivMyGroupSelectedMenu.setOnClickListener { showMyGroupMenu() }
            root.setOnClickListener { layoutMyGroupSelectedMenu.visibility = View.INVISIBLE }
            layoutMyGroupSelectedMenuCopy.setOnClickListener { copyGroupCode() }
            layoutMyGroupSelectedMenuShare.setOnClickListener { shareGroupCode() }
        }
    }

    private fun collectData() {
        viewModel.myGroupListState.flowWithLifecycle(lifecycle).onEach { myGroupListState ->
            when (myGroupListState) {
                is UiState.Success -> {
                    myGroupListState.data.find { it.id == viewModel.getMyGroupId() }
                        ?.let { viewModel.setSelectedMyGroup(it) }
                    viewModel.myGroupList = myGroupListState.data
                }

                else -> Unit
            }
        }.launchIn(lifecycleScope)

        viewModel.selectedMyGroup.flowWithLifecycle(lifecycle).onEach { selectedMyGroup ->
            adapter.submitList(viewModel.myGroupList.filterNot { it == selectedMyGroup })
            binding.ivMyGroupSelectedOwner.visibility = if (selectedMyGroup?.isOwner == true) View.VISIBLE else View.INVISIBLE
        }.launchIn(lifecycleScope)
    }

    private fun showMyGroupMenu() {
        with(binding) {
            if (layoutMyGroupSelectedMenu.visibility == View.VISIBLE) {
                layoutMyGroupSelectedMenu.visibility = View.INVISIBLE
            } else {
                layoutMyGroupSelectedMenu.visibility = View.VISIBLE
            }
        }
    }

    private fun showChangeGroupModal(clickedEntity: MyGroupEntity) {
        binding.layoutMyGroupSelectedMenu.visibility = View.INVISIBLE
        MyGroupModalDialogFragment(
            title = getString(
                R.string.my_group_modal_move_question,
                clickedEntity.name
            ),
            buttonText = stringOf(R.string.my_group_modal_change),
            textButtonText = stringOf(R.string.my_group_modal_back),
            clickBtn = { changeMyGroup(clickedEntity) },
            clickTextBtn = { }
        ).show(supportFragmentManager, CHANGE_MODAL)
    }

    private fun changeMyGroup(clickedEntity: MyGroupEntity) {
        viewModel.changeMyGroupInfo(clickedEntity)
        PingleSnackbar.makeSnackbar(
            view = binding.root,
            message = stringOf(R.string.my_group_snack_bar_chage_group_complete),
            bottomMarin = SNACKBAR_BOTTOM_MARGIN,
            snackbarType = SnackbarType.CHECK
        )
    }

    private fun copyGroupCode() {
        with(binding) {
            layoutMyGroupSelectedMenu.visibility = View.INVISIBLE
            root.copyGroupCode(viewModel.getGroupCode())
        }
    }

    private fun shareGroupCode() {
        binding.layoutMyGroupSelectedMenu.visibility = View.INVISIBLE
        this.sharePingle(getString(R.string.my_group_share_pingle, viewModel.getGroupName(), viewModel.getGroupName(), viewModel.getGroupCode(), PINGLE_PLAY_STORE_LINK))
    }

    private fun navigateToNewGroupInfo() {
        Intent(this, OnboardingActivity::class.java).apply {
            startActivity(this)
        }
    }

    companion object {
        private const val CHANGE_MODAL = "ChangeGroupModal"
        private const val SNACKBAR_BOTTOM_MARGIN = 57
    }
}
