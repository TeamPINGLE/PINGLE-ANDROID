package org.sopt.pingle.presentation.ui.main.home.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentMapBinding
import org.sopt.pingle.domain.model.PinEntity
import org.sopt.pingle.domain.model.PingleEntity
import org.sopt.pingle.presentation.mapper.toMarkerModel
import org.sopt.pingle.presentation.type.CategoryType
import org.sopt.pingle.presentation.ui.main.home.mainlist.MainListFragment
import org.sopt.pingle.util.base.BindingFragment
import org.sopt.pingle.util.component.AllModalDialogFragment
import org.sopt.pingle.util.component.PingleChip
import org.sopt.pingle.util.fragment.navigateToFragment
import org.sopt.pingle.util.fragment.navigateToWebView
import org.sopt.pingle.util.fragment.stringOf
import org.sopt.pingle.util.view.UiState

@AndroidEntryPoint
class MapFragment : BindingFragment<FragmentMapBinding>(R.layout.fragment_map), OnMapReadyCallback {
    private val mapViewModel by viewModels<MapViewModel>()
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
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
                mapViewModel.clearSelectedMarkerPosition()
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
        with(binding) {
            chipMapCategoryPlay.setChipCategoryType(CategoryType.PLAY)
            chipMapCategoryStudy.setChipCategoryType(CategoryType.STUDY)
            chipMapCategoryMulti.setChipCategoryType(CategoryType.MULTI)
            chipMapCategoryOthers.setChipCategoryType(CategoryType.OTHERS)
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

        binding.cgMapCategory.setOnCheckedStateChangeListener { group, checkedIds ->
            mapViewModel.setCategory(
                category = checkedIds.getOrNull(SINGLE_SELECTION)
                    ?.let { group.findViewById<PingleChip>(it).categoryType }
            )
        }

        binding.fabMapList.setOnClickListener {
            navigateToFragment<MainListFragment>()
        }
    }

    private fun collectData() {
        mapViewModel.category.flowWithLifecycle(lifecycle).onEach { category ->
            mapViewModel.getPinListWithoutFilter()
        }.launchIn(lifecycleScope)

        mapViewModel.pinEntityListState.flowWithLifecycle(lifecycle).onEach { uiState ->
            when (uiState) {
                is UiState.Success -> {
                    if (::naverMap.isInitialized) {
                        makeMarkers(uiState.data)
                        with(binding) {
                            fabMapHere.visibility = View.VISIBLE
                            fabMapList.visibility = View.VISIBLE
                            cardMap.visibility = View.INVISIBLE
                        }

                        mapViewModel.clearSelectedMarkerPosition()
                    }
                }

                else -> Unit
            }
        }.launchIn(lifecycleScope)

        mapViewModel.selectedMarkerPosition.flowWithLifecycle(lifecycle)
            .onEach { selectedMarkerPosition ->
                (selectedMarkerPosition == MapViewModel.DEFAULT_SELECTED_MARKER_POSITION).run {
                    with(binding) {
                        fabMapHere.visibility = if (this@run) View.VISIBLE else View.INVISIBLE
                        fabMapList.visibility = if (this@run) View.VISIBLE else View.INVISIBLE
                        cardMap.visibility = if (this@run) View.INVISIBLE else View.VISIBLE
                    }
                }
            }.launchIn(lifecycleScope)

        mapViewModel.pingleListState.flowWithLifecycle(lifecycle).onEach { uiState ->
            when (uiState) {
                is UiState.Success -> {
                    with(binding.cardMap) {
                        initLayout(uiState.data.second[SINGLE_SELECTION])
                        setPinId(uiState.data.first)
                        setOnChatButtonClick {
                            startActivity(navigateToWebView(uiState.data.second[SINGLE_SELECTION].chatLink))
                        }
                        setOnParticipateButtonClick {
                            when (uiState.data.second[SINGLE_SELECTION].isParticipating) {
                                true -> showMapCancelModalDialogFragment(uiState.data.second[SINGLE_SELECTION])
                                false -> showMapModalDialogFragment(uiState.data.second[SINGLE_SELECTION])
                            }
                        }
                    }
                }

                else -> Unit
            }
        }.launchIn(lifecycleScope)

        mapViewModel.pingleParticipationState.flowWithLifecycle(lifecycle).onEach { uiState ->
            when (uiState) {
                is UiState.Success -> {
                    with(binding.cardMap) {
                        pinId?.let { mapViewModel.getPingleList(pinId = it) }
                    }
                }

                else -> Unit
            }
        }.launchIn(lifecycleScope)
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
        mapViewModel.clearMarkerList()

        pinEntityList.mapIndexed { index, pinEntity ->
            pinEntity.toMarkerModel().apply {
                this.marker.apply {
                    map = naverMap
                    setOnClickListener {
                        mapViewModel.updateMarkerModelListSelectedValue(index)
                        mapViewModel.getPingleList(pinEntity.id)
                        moveMapCamera(position)
                        return@setOnClickListener true
                    }
                }
                mapViewModel.addMarkerList(this)
            }
        }
    }

    private fun showMapCancelModalDialogFragment(pingleEntity: PingleEntity) {
        AllModalDialogFragment(
            title = stringOf(R.string.map_cancel_modal_title),
            detail = stringOf(R.string.map_cancel_modal_detail),
            buttonText = stringOf(R.string.map_cancel_modal_button_text),
            textButtonText = stringOf(R.string.map_cancel_modal_text_button_text),
            clickBtn = { mapViewModel.postPingleCancel(meetingId = pingleEntity.id) },
            clickTextBtn = { }
        ).show(childFragmentManager, MAP_CANCEL_MODAL)
    }

    private fun showMapModalDialogFragment(pingleEntity: PingleEntity) {
        with(pingleEntity) {
            MapModalDialogFragment(
                category = CategoryType.fromString(categoryName = category),
                name = name,
                ownerName = ownerName,
                clickBtn = { mapViewModel.postPingleJoin(meetingId = pingleEntity.id) }
            ).show(childFragmentManager, MAP_MODAL)
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
        private val LOCATION_PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        private const val SINGLE_SELECTION = 0
        private const val MAP_CANCEL_MODAL = "mapCancelModal"
        private const val MAP_MODAL = "mapModal"

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
    }
}
