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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentHomeBinding
import org.sopt.pingle.presentation.model.SearchModel
import org.sopt.pingle.presentation.type.CategoryType
import org.sopt.pingle.presentation.type.HomeViewType
import org.sopt.pingle.presentation.ui.main.home.mainlist.MainListFragment
import org.sopt.pingle.presentation.ui.main.home.map.MapFragment
import org.sopt.pingle.presentation.ui.search.SearchActivity
import org.sopt.pingle.presentation.ui.search.SearchActivity.Companion.SEARCH_WORD
import org.sopt.pingle.util.base.BindingFragment
import org.sopt.pingle.util.component.PingleChip
import org.sopt.pingle.util.view.PingleFragmentStateAdapter
import org.sopt.pingle.util.view.UiState

@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var fragmentList: ArrayList<Fragment>
    private lateinit var fragmentStateAdapter: PingleFragmentStateAdapter
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var stopSearchCallback: OnBackPressedCallback

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        addListeners()
        collectData()
        setFragmentStateAdapter()
        setResultLauncher()
        setStopSearchCallback()
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
            }

            cgHomeCategory.setOnCheckedStateChangeListener { group, checkedIds ->
                homeViewModel.setCategory(
                    category = checkedIds.getOrNull(SINGLE_SELECTION)
                        ?.let { group.findViewById<PingleChip>(it).categoryType }
                )
            }

            fabHomeChange.setOnClickListener {
                with(homeViewModel) {
                    when (homeViewType.value) {
                        HomeViewType.MAP -> setHomeViewType(HomeViewType.MAIN_LIST)
                        HomeViewType.MAIN_LIST -> setHomeViewType(HomeViewType.MAP)
                    }
                }
            }
        }
    }

    private fun collectData() {
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

                    (!searchWord.isNullOrBlank()).let { isSearching ->
                        pingleSearchHomeSearch.visibility =
                            if (isSearching) View.VISIBLE else View.INVISIBLE
                        tvHomeGroup.visibility = if (isSearching) View.INVISIBLE else View.VISIBLE
                        ivHomeSearch.visibility = if (isSearching) View.INVISIBLE else View.VISIBLE
                        if (isSearching) setStopSearchCallback() else stopSearchCallback.remove()
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        homeViewModel.mapPingleListState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { uiState ->
                when (uiState) {
                    is UiState.Empty -> {
                        binding.fabHomeChange.visibility = View.VISIBLE
                    }

                    is UiState.Success -> {
                        binding.fabHomeChange.visibility =
                            if (uiState.data.second.isNotEmpty()) View.INVISIBLE else View.VISIBLE
                    }

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
                    homeViewModel.setSearchWord(
                        activityResult.data?.getStringExtra(SEARCH_WORD)
                    )
                }
            }
    }

    private fun setStopSearchCallback() {
        stopSearchCallback =
            object : OnBackPressedCallback(!homeViewModel.searchWord.value.isNullOrEmpty()) {
                override fun handleOnBackPressed() {
                    homeViewModel.clearSearchWord()
                    navigateToSearch()
                }
            }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            stopSearchCallback
        )
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
    }
}
