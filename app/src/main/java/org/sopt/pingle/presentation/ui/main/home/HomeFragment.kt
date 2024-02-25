package org.sopt.pingle.presentation.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentHomeBinding
import org.sopt.pingle.presentation.type.CategoryType
import org.sopt.pingle.presentation.ui.main.home.mainlist.MainListFragment
import org.sopt.pingle.presentation.ui.main.home.map.MapFragment
import org.sopt.pingle.presentation.ui.search.SearchActivity
import org.sopt.pingle.util.base.BindingFragment
import org.sopt.pingle.util.component.PingleChip
import org.sopt.pingle.util.view.PingleFragmentStateAdapter

@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var fragmentList: ArrayList<Fragment>
    private lateinit var fragmentStateAdapter: PingleFragmentStateAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        addListeners()
        collectData()
        setFragmentStateAdapter()
    }

    private fun initLayout() {
        with(binding) {
            chipHomeCategoryPlay.setChipCategoryType(CategoryType.PLAY)
            chipHomeCategoryStudy.setChipCategoryType(CategoryType.STUDY)
            chipHomeCategoryMulti.setChipCategoryType(CategoryType.MULTI)
            chipHomeCategoryOthers.setChipCategoryType(CategoryType.OTHERS)

            when (homeViewModel.category.value) {
                CategoryType.PLAY -> chipHomeCategoryPlay.isChecked = true
                CategoryType.STUDY -> chipHomeCategoryStudy.isChecked = true
                CategoryType.MULTI -> chipHomeCategoryMulti.isChecked = true
                CategoryType.OTHERS -> chipHomeCategoryOthers.isChecked = true
                else -> Unit
            }

            tvHomeGroup.text = homeViewModel.getGroupName()

            with(pingleSearchHomeSearch.binding.etSearchPingleEditText) {
                setText(homeViewModel.searchWord)
                isFocusable = false
                setOnClickListener {
                    navigateToSearch()
                }
            }

            pingleSearchHomeSearch.binding.ivSearchPingleClear.setOnClickListener {
                navigateToSearch()
            }

            (homeViewModel.searchWord.isBlank()).let { isNotSearching ->
                pingleSearchHomeSearch.visibility =
                    if (isNotSearching) View.GONE else View.VISIBLE
                tvHomeGroup.visibility = if (isNotSearching) View.VISIBLE else View.GONE
                ivHomeSearch.visibility = if (isNotSearching) View.VISIBLE else View.GONE
            }
        }
    }

    private fun addListeners() {
        with(binding) {
            ivHomeSearch.setOnClickListener {
                navigateToSearch()
            }

            cgHomeCategory.setOnCheckedStateChangeListener { group, checkedIds ->
                homeViewModel.setCategory(
                    category = checkedIds.getOrNull(SINGLE_SELECTION)
                        ?.let { group.findViewById<PingleChip>(it).categoryType }
                )
            }

            fabHomeChange.setOnClickListener {
                vpHome.setCurrentItem(
                    when (vpHome.currentItem) {
                        MAP_INDEX -> MAIN_LIST_INDEX
                        MAIN_LIST_INDEX -> MAP_INDEX
                        else -> vpHome.currentItem
                    },
                    false
                )
            }
        }
    }

    private fun collectData() {
        homeViewModel.category.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .distinctUntilChanged()
            .onEach {
                homeViewModel.getPinListWithoutFilter()
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        homeViewModel.markerModelData.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { markerModelData ->
                (markerModelData.first == HomeViewModel.DEFAULT_SELECTED_MARKER_POSITION).let { isMarkerUnselected ->
                    binding.fabHomeChange.visibility =
                        if (isMarkerUnselected) View.VISIBLE else View.INVISIBLE
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
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
                        }
                    }
                }
            })
        }
    }

    private fun navigateToSearch() {
        Intent(requireContext(), SearchActivity::class.java).apply {
            startActivity(this)
        }
    }

    companion object {
        private const val SINGLE_SELECTION = 0
        const val MAP_INDEX = 0
        const val MAIN_LIST_INDEX = 1
    }
}
