package org.sopt.pingle.presentation.ui.main.home.map

import android.os.Bundle
import android.view.View
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentMapBinding
import org.sopt.pingle.util.base.BindingFragment

class MapFragment : BindingFragment<FragmentMapBinding>(R.layout.fragment_map) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMapFragment()
    }

    private fun initMapFragment() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.fragment_map_naver_map) as MapFragment?
        mapFragment?.getMapAsync { naverMap ->
            with(naverMap) {
                isNightModeEnabled = true
                mapType = NaverMap.MapType.Navi
            }

            with(naverMap.uiSettings) {
                isZoomControlEnabled = false
                isScaleBarEnabled = false
            }
        }
    }
}
