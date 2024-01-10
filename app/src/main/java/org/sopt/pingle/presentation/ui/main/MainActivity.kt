package org.sopt.pingle.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityMainBinding
import org.sopt.pingle.presentation.ui.main.commend.CommendFragment
import org.sopt.pingle.presentation.ui.main.home.map.MapFragment
import org.sopt.pingle.presentation.ui.main.mypingle.MyPingleFragment
import org.sopt.pingle.presentation.ui.main.plan.planannouncement.PlanAnnouncementActivity
import org.sopt.pingle.presentation.ui.main.setting.SettingFragment
import org.sopt.pingle.util.base.BindingActivity

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        initBnvMainAllNaviItemSelectedListener()
    }

    override fun onResume() {
        super.onResume()

        updateBnvMainAllNaviSelectedItemByFragment()
    }

    private fun initLayout() {
        supportFragmentManager.findFragmentById(R.id.fcv_main_all_navi)
            ?: navigateToFragment<MapFragment>()
    }

    private fun initBnvMainAllNaviItemSelectedListener() {
        binding.bnvMainAllNavi.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_all_navi_home -> navigateToFragment<MapFragment>()
                R.id.menu_all_navi_commend -> navigateToFragment<CommendFragment>()
                R.id.menu_all_navi_add -> navigateToPlanAnnouncement()
                R.id.menu_all_navi_mypingle -> navigateToFragment<MyPingleFragment>()
                R.id.menu_all_navi_setting -> navigateToFragment<SettingFragment>()
            }
            true
        }
    }

    private fun updateBnvMainAllNaviSelectedItemByFragment() {
        binding.bnvMainAllNavi.selectedItemId =
            when (supportFragmentManager.findFragmentById(R.id.fcv_main_all_navi)) {
                is CommendFragment -> R.id.menu_all_navi_commend
                is MyPingleFragment -> R.id.menu_all_navi_mypingle
                is SettingFragment -> R.id.menu_all_navi_setting
                else -> R.id.menu_all_navi_home
            }
    }

    private fun navigateToPlanAnnouncement() {
        Intent(this@MainActivity, PlanAnnouncementActivity::class.java).apply {
            startActivity(this)
        }
    }

    private inline fun <reified T : Fragment> navigateToFragment() {
        if (supportFragmentManager.findFragmentById(R.id.fcv_main_all_navi) !is T) {
            supportFragmentManager.commit {
                replace<T>(R.id.fcv_main_all_navi, T::class.java.canonicalName)
            }
        }
    }
}
