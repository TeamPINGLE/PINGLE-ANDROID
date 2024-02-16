package org.sopt.pingle.presentation.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentHomeBinding
import org.sopt.pingle.presentation.type.CategoryType
import org.sopt.pingle.presentation.ui.main.home.mainlist.MainListFragment
import org.sopt.pingle.presentation.ui.main.home.map.MapFragment
import org.sopt.pingle.util.base.BindingFragment
import org.sopt.pingle.util.view.PingleFragmentStateAdapter

class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private lateinit var fragmentList: ArrayList<Fragment>
    private lateinit var fragmentStateAdapter: PingleFragmentStateAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        addListeners()
        setFragmentStateAdapter()
    }

    private fun initLayout() {
        with(binding) {
            chipHomeCategoryPlay.setChipCategoryType(CategoryType.PLAY)
            chipHomeCategoryStudy.setChipCategoryType(CategoryType.STUDY)
            chipHomeCategoryMulti.setChipCategoryType(CategoryType.MULTI)
            chipHomeCategoryOthers.setChipCategoryType(CategoryType.OTHERS)
        }
    }

    private fun addListeners() {
        with(binding) {
            fabHomeChange.setOnClickListener {
                vpHome.setCurrentItem(
                    when (vpHome.currentItem) {
                        MAP_INDEX -> MAIN_LIST_INDEX
                        else -> MAP_INDEX
                    }, false
                )
            }
        }
    }

    private fun setFragmentStateAdapter() {
        fragmentList = ArrayList()
        fragmentList.apply {
            add(MapFragment())
            add(MainListFragment())
        }

        fragmentStateAdapter = PingleFragmentStateAdapter(fragmentList, requireActivity())

        with(binding.vpHome) {
            adapter = fragmentStateAdapter
            isUserInputEnabled = false
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    with(binding) {
                        (vpHome.currentItem == MAP_INDEX).let { isMap ->
                            fabHomeChange.setImageResource(if (isMap) R.drawable.ic_map_list_24 else R.drawable.ic_map_map_24)
                            fabHomeHere.visibility = if (isMap) View.VISIBLE else View.INVISIBLE
                        }
                    }
                }
            })
        }
    }

    companion object {
        const val MAP_INDEX = 0
        const val MAIN_LIST_INDEX = 1
    }
}