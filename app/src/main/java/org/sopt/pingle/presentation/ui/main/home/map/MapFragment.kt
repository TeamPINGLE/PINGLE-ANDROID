package org.sopt.pingle.presentation.ui.main.home.map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
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
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentMapBinding
import org.sopt.pingle.presentation.mapper.toMarker
import org.sopt.pingle.presentation.type.CategoryType
import org.sopt.pingle.util.base.BindingFragment

class MapFragment : BindingFragment<FragmentMapBinding>(R.layout.fragment_map), OnMapReadyCallback {
    private val mapViewModel by viewModels<MapViewModel>()
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = mapViewModel
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        initLayout()
        initNaverMap()
        requestLocationPermission()
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

        checkLocationPermission()
        makeMarkers()
    }

    private fun initLayout() {
        with(binding) {
            chipMapCategoryPlay.setChipCategoryType(CategoryType.PLAY)
            chipMapCategoryStudy.setChipCategoryType(CategoryType.STUDY)
            chipMapCategoryMulti.setChipCategoryType(CategoryType.MULTI)
            chipMapCategoryOthers.setChipCategoryType(CategoryType.OTHER)

            fabMapHere.setOnClickListener {
                locationSource.lastLocation?.let { location -> moveMapCamera(location) }
            }
        }
    }

    private fun initNaverMap() {
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

    private fun checkLocationPermission() {
        if (LOCATION_PERMISSIONS.any {
                ActivityCompat.checkSelfPermission(
                    requireContext(),
                    it
                ) == PackageManager.PERMISSION_GRANTED
            }) {
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
            location?.let { moveMapCamera(it) }
        }
    }

    private fun requestLocationPermission() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {

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
                ).animate(CameraAnimation.Fly)
            )
        }
    }

    private fun makeMarkers() {
        mapViewModel.dummyPinList.map { pinEntity ->
            pinEntity.toMarker().map = naverMap
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
        private val LOCATION_PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }
}
