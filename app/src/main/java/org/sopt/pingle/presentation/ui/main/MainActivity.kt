package org.sopt.pingle.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityMainBinding
import org.sopt.pingle.presentation.ui.main.home.HomeFragment
import org.sopt.pingle.presentation.ui.main.home.map.MapFragment
import org.sopt.pingle.presentation.ui.main.more.MoreFragment
import org.sopt.pingle.presentation.ui.main.mypingle.MyPingleFragment
import org.sopt.pingle.presentation.ui.main.planannouncement.PlanAnnouncementActivity
import org.sopt.pingle.presentation.ui.main.ranking.RankingFragment
import org.sopt.pingle.util.activity.setDoubleBackPressToExit
import org.sopt.pingle.util.base.BindingActivity

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Pingle)
        super.onCreate(savedInstanceState)

        installSplashScreen()
        initLayout()
        initBnvMainAllNaviItemSelectedListener()
        setDoubleBackPressToExit(binding.root)
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
                R.id.menu_all_navi_home -> navigateToFragment<HomeFragment>()
                R.id.menu_all_navi_ranking -> navigateToFragment<RankingFragment>()
                R.id.menu_all_navi_plan -> navigateToPlanAnnouncement()
                R.id.menu_all_navi_mypingle -> navigateToFragment<MyPingleFragment>()
                R.id.menu_all_navi_more -> navigateToFragment<MoreFragment>()
            }
            true
        }
    }

    private fun updateBnvMainAllNaviSelectedItemByFragment() {
        binding.bnvMainAllNavi.selectedItemId =
            when (supportFragmentManager.findFragmentById(R.id.fcv_main_all_navi)) {
                is RankingFragment -> R.id.menu_all_navi_ranking
                is MyPingleFragment -> R.id.menu_all_navi_mypingle
                is MoreFragment -> R.id.menu_all_navi_more
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
