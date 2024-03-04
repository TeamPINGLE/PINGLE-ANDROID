package org.sopt.pingle.presentation.ui.main.home.map

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentMapBinding
import org.sopt.pingle.domain.model.PinEntity
import org.sopt.pingle.presentation.mapper.toMarkerModel
import org.sopt.pingle.presentation.type.HomeViewType
import org.sopt.pingle.presentation.ui.main.home.HomeViewModel
import org.sopt.pingle.presentation.ui.main.home.HomeViewModel.Companion.DEFAULT_SELECTED_MARKER_POSITION
import org.sopt.pingle.util.base.BindingFragment
import org.sopt.pingle.util.fragment.navigateToWebView
import org.sopt.pingle.util.toPx
import org.sopt.pingle.util.view.PingleCardUtils
import org.sopt.pingle.util.view.UiState

@AndroidEntryPoint
class MapFragment : BindingFragment<FragmentMapBinding>(R.layout.fragment_map), OnMapReadyCallback {
    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private lateinit var mapCardAdapter: MapCardAdapter
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions[LOCATION_PERMISSIONS[0]] == true || permissions[LOCATION_PERMISSIONS[1]] == true -> {
                setLocationTrackingMode()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMap()
        initLayout()
        addListeners()
    }

    override fun onDestroyView() {
        binding.vpMapCard.adapter = null
        super.onDestroyView()
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap.apply {
            isNightModeEnabled = true
            mapType = NaverMap.MapType.Navi
            minZoom = MIN_ZOOM

            with(uiSettings) {
                isZoomControlEnabled = false
                isScaleBarEnabled = false
                isCompassEnabled = false
            }

            setOnMapClickListener { _, _ ->
                homeViewModel.clearSelectedMarkerPosition()
                homeViewModel.initMapPingleListState()
            }
        }

        collectData()
        setLocationTrackingMode()
    }

    private fun initMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.fragment_map_naver_map) as MapFragment?
                ?: MapFragment.newInstance().also {
                    childFragmentManager.commit {
                        add<MapFragment>(R.id.fragment_map_naver_map)
                        setReorderingAllowed(true)
                    }
                }

        mapFragment.getMapAsync(this@MapFragment)
    }

    private fun initLayout() {
        mapCardAdapter = MapCardAdapter(
            navigateToParticipant = { id ->
                context?.let {
                    PingleCardUtils.navigateToParticipant(
                        it,
                        id
                    )
                }
            },
            navigateToWebViewWithChatLink = { chatLink -> startActivity(navigateToWebView(chatLink)) },
            showPingleJoinModalDialogFragment = { pingleEntity ->
                PingleCardUtils.showPingleJoinModalDialogFragment(
                    fragment = this,
                    postPingleJoin = { homeViewModel.postPingleJoin(pingleEntity.id) },
                    pingleEntity = pingleEntity
                )
            },
            showPingleCancelModalDialogFragment = { pingleEntity ->
                PingleCardUtils.showPingleCancelModalDialogFragment(
                    fragment = this,
                    deletePingleCancel = { homeViewModel.deletePingleCancel(pingleEntity.id) }
                )
            },
            showPingleDeleteModalDialogFragment = { pingleEntity ->
                PingleCardUtils.showMapDeleteModalDialogFragment(
                    fragment = this,
                    deletePingleDelete = { homeViewModel.deletePingleDelete(pingleEntity.id) }
                )
            }
        )

        with(binding.vpMapCard) {
            adapter = mapCardAdapter
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.right = VIEWPAGER_ITEM_OFFSET.toPx()
                    outRect.left = VIEWPAGER_ITEM_OFFSET.toPx()
                }
            })
            offscreenPageLimit = 1
            setPageTransformer { page, position ->
                page.translationX = (VIEWPAGER_PAGE_TRANSFORMER).toPx() * position
            }
        }
    }

    private fun addListeners() {
        binding.fabMapHere.setOnClickListener {
            if (::locationSource.isInitialized) {
                locationSource.lastLocation?.let { location ->
                    moveMapCamera(
                        LatLng(
                            location.latitude,
                            location.longitude
                        )
                    )
                }
            }
        }
    }

    private fun collectData() {
        combine(
            homeViewModel.category.flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .distinctUntilChanged(),
            homeViewModel.searchWord.flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .distinctUntilChanged()
        ) { _, _ ->
        }.onEach {
            homeViewModel.getPinListWithoutFilter()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        homeViewModel.markerModelData.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { markerModelData ->
                (markerModelData.first == DEFAULT_SELECTED_MARKER_POSITION).let { isMarkerUnselected ->
                    with(binding) {
                        fabMapHere.visibility =
                            if (isMarkerUnselected) View.VISIBLE else View.INVISIBLE
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        homeViewModel.pinEntityListState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .distinctUntilChanged()
            .onEach { uiState ->
                when (uiState) {
                    is UiState.Success -> {
                        if (::naverMap.isInitialized) {
                            makeMarkers(uiState.data)
                            mapCardAdapter.clearData()
                            homeViewModel.clearSelectedMarkerPosition()
                        }

                        homeViewModel.searchWord.value?.let { searchWord ->
                            when {
                                uiState.data.isEmpty() -> homeViewModel.setHomeViewType(HomeViewType.MAIN_LIST)
                                else -> moveMapCamera(homeViewModel.markerModelData.value.second[FIRST_INDEX].marker.position)
                            }
                        }
                    }

                    else -> Unit
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        homeViewModel.mapPingleListState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { uiState ->
                when (uiState) {
                    is UiState.Empty -> {
                        binding.fabMapHere.visibility = View.VISIBLE
                        mapCardAdapter.clearData()
                    }

                    is UiState.Success -> {
                        uiState.data.first.let { pinId ->
                            mapCardAdapter.setPinId(pinId = pinId)
                            homeViewModel.markerModelData.value.second.withIndex().firstOrNull { markerModel -> markerModel.value.id == pinId }?.let { (position, markerModel) ->
                                moveMapCamera(markerModel.marker.position)
                                homeViewModel.updateMarkerModelListSelectedValue(position = position)
                            }
                        }

                        uiState.data.second.let { mapPingleList ->
                            mapCardAdapter.submitList(mapPingleList)
                            binding.fabMapHere.visibility =
                                if (mapPingleList.isNotEmpty()) View.INVISIBLE else View.VISIBLE
                        }
                    }

                    else -> Unit
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        homeViewModel.pingleParticipationState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { uiState ->
                when (uiState) {
                    is UiState.Success -> {
                        mapCardAdapter.pinId.takeIf { it != DEFAULT_VALUE }?.let { pinId ->
                            homeViewModel.getMapPingleList(pinId = pinId)
                        }
                    }

                    else -> Unit
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        homeViewModel.pingleDeleteState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { uiState ->
                when (uiState) {
                    is UiState.Success -> {
                        homeViewModel.getPinListWithoutFilter()

                        mapCardAdapter.pinId.takeIf { it != DEFAULT_VALUE }?.let { pinId ->
                            homeViewModel.getMapPingleList(pinId = pinId)
                        }
                    }

                    else -> Unit
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setLocationTrackingMode() {
        if (LOCATION_PERMISSIONS.any { permission ->
            ContextCompat.checkSelfPermission(
                    requireContext(),
                    permission
                ) == PackageManager.PERMISSION_GRANTED
        }
        ) {
            locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

            with(naverMap) {
                locationSource = this@MapFragment.locationSource
                locationTrackingMode = LocationTrackingMode.NoFollow

                locationOverlay.apply {
                    isVisible = true
                    icon = OverlayImage.fromResource(R.drawable.ic_map_location_overlay_20)
                }
            }
        } else {
            locationPermissionRequest.launch(LOCATION_PERMISSIONS)
        }

        LocationServices.getFusedLocationProviderClient(requireContext()).lastLocation.addOnSuccessListener { location ->
            location?.let {
                moveMapCamera(LatLng(it.latitude, it.longitude))
                naverMap.locationOverlay.position = LatLng(it.latitude, it.longitude)
            }
        }
    }

    private fun moveMapCamera(latLng: LatLng) {
        if (::naverMap.isInitialized) {
            naverMap.moveCamera(
                CameraUpdate.scrollTo(latLng).animate(CameraAnimation.Linear)
            )
        }
    }

    private fun makeMarkers(pinEntityList: List<PinEntity>) {
        homeViewModel.clearMarkerModelData()

        pinEntityList.mapIndexed { index, pinEntity ->
            pinEntity.toMarkerModel().apply {
                this.marker.apply {
                    map = naverMap
                    setOnClickListener {
                        homeViewModel.getMapPingleList(pinEntity.id)
                        return@setOnClickListener true
                    }
                }
                homeViewModel.addMarkerModelList(this)
            }
        }
    }

    companion object {
        private const val FIRST_INDEX = 0
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
        private val LOCATION_PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        val OVERLAY_IMAGE_PIN_PLAY_DEFAULT =
            OverlayImage.fromResource(R.drawable.ic_pin_play_default)
        val OVERLAY_IMAGE_PIN_STUDY_DEFAULT =
            OverlayImage.fromResource(R.drawable.ic_pin_study_default)
        val OVERLAY_IMAGE_PIN_MULTI_DEFAULT =
            OverlayImage.fromResource(R.drawable.ic_pin_multi_default)
        val OVERLAY_IMAGE_PIN_OTHERS_DEFAULT =
            OverlayImage.fromResource(R.drawable.ic_pin_others_default)
        val OVERLAY_IMAGE_PIN_PLAY_ACTIVE = OverlayImage.fromResource(R.drawable.ic_pin_play_active)
        val OVERLAY_IMAGE_PIN_STUDY_ACTIVE =
            OverlayImage.fromResource(R.drawable.ic_pin_study_active)
        val OVERLAY_IMAGE_PIN_MULTI_ACTIVE =
            OverlayImage.fromResource(R.drawable.ic_pin_multi_active)
        val OVERLAY_IMAGE_PIN_OTHERS_ACTIVE =
            OverlayImage.fromResource(R.drawable.ic_pin_others_active)

        private const val MIN_ZOOM = 5.5
        private const val DEFAULT_VALUE = -1L

        private const val VIEWPAGER_ITEM_OFFSET = 24
        private const val VIEWPAGER_PAGE_TRANSFORMER = -40

        const val MEETING_ID = "meetingId"
    }
}
