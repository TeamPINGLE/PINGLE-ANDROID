package org.sopt.pingle.presentation.ui.main.home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentHomeBinding
import org.sopt.pingle.presentation.model.SearchModel
import org.sopt.pingle.presentation.type.CategoryType
import org.sopt.pingle.presentation.type.HomeViewType
import org.sopt.pingle.presentation.type.SnackbarType
import org.sopt.pingle.presentation.ui.main.home.mainlist.MainListFragment
import org.sopt.pingle.presentation.ui.main.home.map.MapFragment
import org.sopt.pingle.presentation.ui.search.SearchActivity
import org.sopt.pingle.presentation.ui.search.SearchActivity.Companion.SEARCH_WORD
import org.sopt.pingle.util.AmplitudeUtils
import org.sopt.pingle.util.activity.FINISH_INTERVAL_TIME
import org.sopt.pingle.util.activity.INIT_BACK_PRESSED_TIME
import org.sopt.pingle.util.base.BindingFragment
import org.sopt.pingle.util.component.PingleChip
import org.sopt.pingle.util.component.PingleSnackbar
import org.sopt.pingle.util.fragment.stringOf
import org.sopt.pingle.util.view.PingleFragmentStateAdapter
import org.sopt.pingle.util.view.UiState

@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val homeViewModel: HomeViewModel by activityViewModels()
    private var backPressedTime = INIT_BACK_PRESSED_TIME
    private lateinit var fragmentList: ArrayList<Fragment>
    private lateinit var fragmentStateAdapter: PingleFragmentStateAdapter
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        addListeners()
        collectData()
        setFragmentStateAdapter()
        setResultLauncher()
    }

    override fun onResume() {
        super.onResume()

        setOnBackPressedCallback()
        initChip()
    }

    private fun initLayout() {
        with(binding) {
            chipHomeCategoryPlay.setChipCategoryType(CategoryType.PLAY)
            chipHomeCategoryStudy.setChipCategoryType(CategoryType.STUDY)
            chipHomeCategoryMulti.setChipCategoryType(CategoryType.MULTI)
            chipHomeCategoryOthers.setChipCategoryType(CategoryType.OTHERS)

            tvHomeGroup.text = homeViewModel.getGroupName()

            with(pingleSearchHomeSearch.binding.etSearchPingleEditText) {
                isFocusable = false
                setOnClickListener {
                    navigateToSearch()
                }
            }

            pingleSearchHomeSearch.binding.ivSearchPingleClear.setOnClickListener {
                homeViewModel.clearSearchWord()
                navigateToSearch()
            }
        }
    }

    private fun addListeners() {
        with(binding) {
            ivHomeSearch.setOnClickListener {
                navigateToSearch()

                when (homeViewModel.homeViewType.value) {
                    HomeViewType.MAP -> AmplitudeUtils.trackEvent(CLICK_SEARCH_MAP)
                    HomeViewType.MAIN_LIST -> AmplitudeUtils.trackEvent(CLICK_SEARCH_LIST)
                }
            }

            cgHomeCategory.setOnCheckedStateChangeListener { group, checkedIds ->
                homeViewModel.setCategory(
                    category = checkedIds.getOrNull(SINGLE_SELECTION)
                        ?.let { group.findViewById<PingleChip>(it).categoryType }
                )

                checkedIds.getOrNull(SINGLE_SELECTION)
                    ?.let {
                        group.findViewById<PingleChip>(it).categoryType.let { categoryType ->
                            when (homeViewModel.homeViewType.value) {
                                HomeViewType.MAP -> AmplitudeUtils.trackEventWithProperty(
                                    eventName = CLICK_CATEGORY_MAP,
                                    propertyName = CATEGORY,
                                    propertyValue = categoryType.name
                                )

                                HomeViewType.MAIN_LIST -> AmplitudeUtils.trackEventWithProperty(
                                    eventName = CLICK_CATEGORY_LIST,
                                    propertyName = CATEGORY,
                                    propertyValue = categoryType.name
                                )
                            }
                        }
                    }
            }

            fabHomeChange.setOnClickListener {
                with(homeViewModel) {
                    when (homeViewType.value) {
                        HomeViewType.MAP -> {
                            AmplitudeUtils.trackEvent(CLICK_LIST_MAP)
                            setHomeViewType(HomeViewType.MAIN_LIST)
                        }

                        HomeViewType.MAIN_LIST -> {
                            AmplitudeUtils.trackEvent(CLICK_MAP_LIST)
                            setHomeViewType(HomeViewType.MAP)
                        }
                    }
                }
            }
        }
    }

    private fun collectData() {
        combine(
            homeViewModel.category.flowWithLifecycle(viewLifecycleOwner.lifecycle).distinctUntilChanged(),
            homeViewModel.searchWord.flowWithLifecycle(viewLifecycleOwner.lifecycle).distinctUntilChanged(),
            homeViewModel.mainListOrderType.flowWithLifecycle(viewLifecycleOwner.lifecycle).distinctUntilChanged(),
            homeViewModel.homeViewType.flowWithLifecycle(viewLifecycleOwner.lifecycle).distinctUntilChanged()
        ) { _, searchWord, _, homeViewType ->
            Pair(searchWord != homeViewModel.lastSearchWord, homeViewType)
        }.onEach { searchStatusAndHomeViewType ->
            when (searchStatusAndHomeViewType.second) {
                HomeViewType.MAIN_LIST -> homeViewModel.getMainListPingleList()
                HomeViewType.MAP -> homeViewModel.getPinListWithoutFilter(isSearching = searchStatusAndHomeViewType.first)
            }
            homeViewModel.setLastSearchWord(homeViewModel.searchWord.value)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        homeViewModel.homeViewType.flowWithLifecycle(viewLifecycleOwner.lifecycle).onEach {
            with(binding) {
                vpHome.setCurrentItem(homeViewModel.homeViewType.value.index, false)
                fabHomeChange.setImageResource(homeViewModel.homeViewType.value.fabDrawableRes)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        homeViewModel.searchWord.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { searchWord ->
                with(binding) {
                    pingleSearchHomeSearch.binding.etSearchPingleEditText.setText(homeViewModel.searchWord.value)

                    (!searchWord.isNullOrEmpty()).let { isSearching ->
                        pingleSearchHomeSearch.visibility =
                            if (isSearching) View.VISIBLE else View.INVISIBLE
                        tvHomeGroup.visibility = if (isSearching) View.INVISIBLE else View.VISIBLE
                        ivHomeSearch.visibility = if (isSearching) View.INVISIBLE else View.VISIBLE
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        homeViewModel.mapPingleListState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { uiState ->
                when (uiState) {
                    is UiState.Empty -> binding.fabHomeChange.visibility = View.VISIBLE

                    is UiState.Success ->
                        binding.fabHomeChange.visibility =
                            if (uiState.data.second.isNotEmpty()) View.INVISIBLE else View.VISIBLE

                    else -> Unit
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
        }
    }

    private fun setResultLauncher() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
                if (activityResult.resultCode == RESULT_OK) {
                    homeViewModel.clearCategory()
                    homeViewModel.setSearchWord(
                        activityResult.data?.getStringExtra(SEARCH_WORD)
                    )
                }
            }
    }

    private fun setOnBackPressedCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(
            this.viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (homeViewModel.searchWord.value.isNullOrEmpty()) {
                        if (System.currentTimeMillis() - backPressedTime <= FINISH_INTERVAL_TIME) {
                            requireActivity().finish()
                        } else {
                            backPressedTime = System.currentTimeMillis()
                            PingleSnackbar.makeSnackbar(
                                view = requireView(),
                                message = stringOf(R.string.all_on_back_pressed_snackbar),
                                bottomMarin = org.sopt.pingle.util.activity.SNACKBAR_BOTTOM_MARGIN,
                                snackbarType = SnackbarType.GUIDE
                            )
                        }
                    } else {
                        homeViewModel.clearSearchWord()
                        navigateToSearch()
                    }
                }
            }
        )
    }

    private fun initChip() {
        with(binding) {
            homeViewModel.category.value.let { selectedCategory ->
                chipHomeCategoryPlay.isChecked = selectedCategory == CategoryType.PLAY
                chipHomeCategoryStudy.isChecked = selectedCategory == CategoryType.STUDY
                chipHomeCategoryMulti.isChecked = selectedCategory == CategoryType.MULTI
                chipHomeCategoryOthers.isChecked = selectedCategory == CategoryType.OTHERS
            }
        }
    }

    private fun navigateToSearch() {
        Intent(requireContext(), SearchActivity::class.java).apply {
            putExtra(
                SEARCH_MODEL,
                SearchModel(
                    homeViewType = homeViewModel.homeViewType.value,
                    searchWord = homeViewModel.searchWord.value
                )
            )
            resultLauncher.launch(this)
        }
    }

    companion object {
        private const val SINGLE_SELECTION = 0
        const val SEARCH_MODEL = "searchModel"
        const val SNACKBAR_BOTTOM_MARGIN = 76
        val DELETED_PINGLE_MESSAGE = listOf("존재하지 않는 번개입니다", "존재하지 않는 유저미팅입니다.")

        private const val CLICK_SEARCH_MAP = "click_search_map"
        private const val CLICK_SEARCH_LIST = "click_search_list"
        private const val CLICK_CATEGORY_MAP = "click_category_map"
        private const val CLICK_CATEGORY_LIST = "click_category_list"
        private const val CATEGORY = "category"
        private const val CLICK_LIST_MAP = "click_list_map"
        private const val CLICK_MAP_LIST = "click_map_list"
    }
}
