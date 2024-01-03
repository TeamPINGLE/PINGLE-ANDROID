package org.sopt.pingle.presentation.ui.main.home.map

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentMapBinding
import org.sopt.pingle.util.base.BindingFragment

class MapFragment : BindingFragment<FragmentMapBinding>(R.layout.fragment_map), OnMapReadyCallback {
    private lateinit var naverMap: NaverMap
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMapFragment()
    }

    private fun initMapFragment() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.fragment_map_naver_map) as MapFragment?
                ?: MapFragment.newInstance().also {
                    childFragmentManager.commit {
                        add<MapFragment>(R.id.fragment_map_naver_map)
                        setReorderingAllowed(true)
                    }
                }

        mapFragment.getMapAsync(this)
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
    }
}