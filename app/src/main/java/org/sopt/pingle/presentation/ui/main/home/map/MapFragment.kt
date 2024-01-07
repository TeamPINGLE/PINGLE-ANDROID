package org.sopt.pingle.presentation.ui.main.home.map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentMapBinding
import org.sopt.pingle.presentation.mapper.toMarker
import org.sopt.pingle.presentation.type.CategoryType
import org.sopt.pingle.presentation.ui.common.AllModalDialogFragment
import org.sopt.pingle.util.base.BindingFragment
import org.sopt.pingle.util.component.OnPingleCardClickListener
import org.sopt.pingle.util.component.PingleChip
import org.sopt.pingle.util.fragment.navigateToWebView
import org.sopt.pingle.util.fragment.showToast
import org.sopt.pingle.util.fragment.stringOf

class MapFragment : BindingFragment<FragmentMapBinding>(R.layout.fragment_map), OnMapReadyCallback {
    private val mapViewModel by viewModels<MapViewModel>()
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMap()
        initLayout()
        addListeners()
        collectData()
        setLocationTrackingMode()
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap.apply {
            isNightModeEnabled = true
            mapType = NaverMap.MapType.Navi

            with(uiSettings) {
                isZoomControlEnabled = false
                isScaleBarEnabled = false
            }
        }

        makeMarkers()
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
            cardMap.initLayout(mapViewModel.dummyPingle)
            cardMap.listener = object : OnPingleCardClickListener {
                override fun onPingleCardChatBtnClickListener() {
                    startActivity(navigateToWebView(mapViewModel.dummyPingle.chatLink))
                }

                override fun onPingleCardParticipateBtnClickListener() {
                    when (mapViewModel.dummyPingle.isParticipating) {
                        true -> showMapCancelModalDialogFragment()
                        false -> showMapModalDialogFragment()
                    }
                }
            }
        }
    }

    private fun addListeners() {
        binding.fabMapHere.setOnClickListener {
            if (::locationSource.isInitialized) {
                locationSource.lastLocation?.let { location -> moveMapCamera(location) }
            }
        }

        binding.cgMapCategory.setOnCheckedStateChangeListener { group, checkedIds ->
            mapViewModel.setCategory(
                category = checkedIds.getOrNull(SINGLE_SELECTION)
                    ?.let { group.findViewById<PingleChip>(it).categoryType }
            )
        }
    }

    private fun collectData() {
        mapViewModel.category.flowWithLifecycle(lifecycle).onEach {
            // TODO 서버 통신 구현 시 삭제 예정
            showToast(it?.name ?: "null")
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
        } else {
            requestLocationPermission()
        }

        LocationServices.getFusedLocationProviderClient(requireContext()).lastLocation.addOnSuccessListener { location ->
            if (::naverMap.isInitialized) {
                with(naverMap) {
                    locationSource = this@MapFragment.locationSource
                    locationTrackingMode = LocationTrackingMode.NoFollow

                    locationOverlay.apply {
                        isVisible = true
                        icon = OverlayImage.fromResource(R.drawable.ic_map_location_overlay)
                    }
                }
            }

            moveMapCamera(location)
        }
    }

    private fun requestLocationPermission() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[LOCATION_PERMISSIONS[0]] == true || permissions[LOCATION_PERMISSIONS[1]] == true -> {
                    setLocationTrackingMode()
                }
            }
        }

        locationPermissionRequest.launch(LOCATION_PERMISSIONS)
    }

    private fun moveMapCamera(location: Location) {
        if (::naverMap.isInitialized) {
            naverMap.moveCamera(
                CameraUpdate.scrollTo(
                    LatLng(
                        location.latitude,
                        location.longitude
                    )
                ).animate(CameraAnimation.Linear)
            )
        }
    }

    private fun makeMarkers() {
        mapViewModel.dummyPinList.map { pinEntity ->
            pinEntity.toMarker().map = naverMap
        }
    }

    private fun showMapCancelModalDialogFragment() {
        AllModalDialogFragment(
            title = stringOf(R.string.map_cancel_modal_title),
            detail = stringOf(R.string.map_cancel_modal_detail),
            buttonText = stringOf(R.string.map_cancel_modal_button_text),
            textButtonText = stringOf(R.string.map_cancel_modal_text_button_text),
            clickBtn = { mapViewModel.cancelPingle() },
            clickTextBtn = { },
            onDialogClosed = { binding.cardMap.initLayout(mapViewModel.dummyPingle) }
        ).show(childFragmentManager, MAP_CANCEL_MODAL)
    }

    private fun showMapModalDialogFragment() {
        with(mapViewModel.dummyPingle) {
            MapModalDialogFragment(
                category = CategoryType.fromString(categoryName = category),
                name = name,
                ownerName = ownerName,
                clickBtn = { mapViewModel.joinPingle() },
                onDialogClosed = { binding.cardMap.initLayout(mapViewModel.dummyPingle) }
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
    }
}
