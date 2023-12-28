package org.sopt.pingle.presentation.ui.dummy

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityDummyBinding
import org.sopt.pingle.util.base.BindingActivity

@AndroidEntryPoint
class DummyActivity : BindingActivity<ActivityDummyBinding>(R.layout.activity_dummy) {
    private val dummyViewModel by viewModels<DummyViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = dummyViewModel
    }

    override fun onDestroy() {
        // TODO adapter를 사용한다면 adapter를 해제시켜주세요.(Fragment라면 onDestroyView에서 해제시켜주면 됩니다.) ex) binding.rvDummy.adpater = null
        super.onDestroy()
    }
}
