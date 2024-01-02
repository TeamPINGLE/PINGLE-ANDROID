package org.sopt.pingle.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityMainBinding
import org.sopt.pingle.presentation.ui.main.commend.CommendFragment
import org.sopt.pingle.presentation.ui.main.home.map.MapFragment
import org.sopt.pingle.presentation.ui.main.mypingle.MyPingleFragment
import org.sopt.pingle.presentation.ui.main.plan.PlanActivity
import org.sopt.pingle.presentation.ui.main.setting.SettingFragment
import org.sopt.pingle.util.base.BindingActivity

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        initBnvMainAllNaviItemSelectedListener()
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
                R.id.menu_all_navi_add -> navigateToPlan()
                R.id.menu_all_navi_mypingle -> navigateToFragment<MyPingleFragment>()
                R.id.menu_all_navi_setting -> navigateToFragment<SettingFragment>()
            }
            true
        }
    }

    private fun navigateToPlan() {
        Intent(this@MainActivity, PlanActivity::class.java).apply {
            startActivity(this)
        }
    }

    private inline fun <reified T : Fragment> navigateToFragment() {
        supportFragmentManager.commit {
            replace<T>(R.id.fcv_main_all_navi, T::class.java.canonicalName)
        }
    }
}
