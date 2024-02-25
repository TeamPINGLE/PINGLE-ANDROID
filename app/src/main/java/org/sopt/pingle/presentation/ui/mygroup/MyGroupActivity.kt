package org.sopt.pingle.presentation.ui.mygroup

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityMyGroupBinding
import org.sopt.pingle.presentation.type.SnackbarType
import org.sopt.pingle.presentation.ui.onboarding.OnBoardingActivity
import org.sopt.pingle.util.base.BindingActivity
import org.sopt.pingle.util.component.AllModalDialogFragment
import org.sopt.pingle.util.component.PingleSnackbar
import org.sopt.pingle.util.context.stringOf

@AndroidEntryPoint
class MyGroupActivity : BindingActivity<ActivityMyGroupBinding>(R.layout.activity_my_group) {

    private val viewModel by viewModels<MyGroupViewModel>()
    private lateinit var onBackPressed: OnBackPressedCallback
    private lateinit var adapter: MyGroupAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.myGroupviewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.Logging()
        initLayout()
        addListeners()
        collectData()
        onBackPressedBtn()
    }

    override fun onDestroy() {
        binding.rvMyGroupList.adapter = null
        super.onDestroy()
    }

    private fun initLayout() {
        checkMyGroupOwner()
        viewModel.getGroupList()

        with(binding) {
            toolbar.text = getString(R.string.my_group_title)
            // TODO chip 머지되면 text에 localStorage에 저장된 keyword 데바

            adapter = MyGroupAdapter(this@MyGroupActivity::showChangeGroupModal)
            rvMyGroupList.adapter = adapter
            adapter.submitList(viewModel.filteredGroupList.value)
        }
    }

    private fun addListeners() {
        with(binding) {
            toolbar.ivAllTopbarArrowWithTitleArrowLeft.setOnClickListener { finish() }
            tvMyGroupMoveNewGroup.setOnClickListener { navigateToNewGroupInfo() }
            ivMyGroupNowMenu.setOnClickListener { showMyGroupMenu() }
            root.setOnClickListener { layoutMyGroupMenu.visibility = View.INVISIBLE }
            layoutMyGroupMenuCopy.setOnClickListener { copyGroupCode() }
            layoutMyGroupMenuShare.setOnClickListener { shareGroupCode() }
        }
    }

    private fun collectData() {
        viewModel.filteredGroupList.flowWithLifecycle(lifecycle).onEach { filteredList ->
            adapter.submitList(filteredList)
        }.launchIn(lifecycleScope)
    }

    private fun checkMyGroupOwner() {
        with(binding) {
            if (viewModel.getMyGroupIsOwner()) {
                ivMyGroupNowOwner.visibility = View.VISIBLE
            } else {
                ivMyGroupNowOwner.visibility = View.INVISIBLE
            }
        }
    }

    private fun showMyGroupMenu() {
        with(binding) {
            if (layoutMyGroupMenu.visibility == View.VISIBLE) {
                layoutMyGroupMenu.visibility = View.INVISIBLE
            } else {
                layoutMyGroupMenu.visibility = View.VISIBLE
            }
        }
    }

    private fun showChangeGroupModal(clickedPosition: Int) {
        binding.layoutMyGroupMenu.visibility = View.INVISIBLE
        AllModalDialogFragment(
            title = getString(R.string.my_group_modal_move_question, viewModel.filteredGroupList.value[clickedPosition].name),
            detail = null,
            buttonText = getString(R.string.my_group_modal_change),
            textButtonText = getString(R.string.my_group_modal_back),
            clickBtn = { chageToNewGroup(clickedPosition) },
            clickTextBtn = { },
        ).show(supportFragmentManager, CHANGE_MODAL)
    }

    private fun chageToNewGroup(clickedPosition: Int) {
        viewModel.changeGroupList(clickedPosition)

        PingleSnackbar.makeSnackbar(
            view = binding.root,
            message = stringOf(R.string.my_group_snack_bar_chage_group_complete),
            bottomMarin = SNACKBAR_BOTTOM_MARGIN,
            snackbarType = SnackbarType.GUIDE,
        )

        viewModel.Logging()
    }

    private fun copyGroupCode() {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText(GROUP_CODE_COPY, viewModel.getGroupCode())
        clipboard.setPrimaryClip(clip)

        PingleSnackbar.makeSnackbar(
            view = binding.root,
            message = stringOf(R.string.my_group_snack_bar_code_copy_complete),
            bottomMarin = SNACKBAR_BOTTOM_MARGIN,
            snackbarType = SnackbarType.GUIDE,
        )
    }

    private fun shareGroupCode() {
        val intent = Intent(Intent.ACTION_SEND_MULTIPLE)
        with(intent) {
            type = SHARE_TYPE
            // TODO 기획에서 전달해준 템플릿 적용
            putExtra(
                Intent.EXTRA_TEXT,
                "핑글 앱을 다운받고, ${viewModel.getGroupName()} 사람들을 만나보세요!\n\n$PINGLE_SHARE_CODE ${viewModel.getGroupCode()} \n\n $PINGLE_PLAY_STORE_LINK",
            )
        }
        startActivity(Intent.createChooser(intent, null))
    }

    private fun navigateToNewGroupInfo() {
        Intent(this, OnBoardingActivity::class.java).apply {
            startActivity(this)
        }
    }

    private fun onBackPressedBtn() {
        onBackPressed = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressed)
    }

    companion object {
        private const val CHANGE_MODAL = "ChangeGroupModal"
        private const val SNACKBAR_BOTTOM_MARGIN = 57
        private const val GROUP_CODE_COPY = "CopyGroupCode"
        private const val PINGLE_PLAY_STORE_LINK =
            "앱 링크 : https://play.google.com/store/apps/details?id=org.sopt.pingle&pcampaignid=web_share"
        private const val PINGLE_SHARE_CODE = "초대코드 : "
        private const val SHARE_TYPE = "text/plain"
    }
}
