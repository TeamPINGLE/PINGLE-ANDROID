package org.sopt.pingle

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.naver.maps.map.NaverMapSdk
import dagger.hilt.android.HiltAndroidApp
import org.sopt.pingle.BuildConfig.NAVER_MAP_CLIENT_ID
import org.sopt.pingle.util.PingleDebugTree
import timber.log.Timber

@HiltAndroidApp
class PingleApp : Application() {
    override fun onCreate() {
        super.onCreate()

        setTimber()
        setDarkMode()
        setNaverMap()
    }

    private fun setTimber() {
        if (BuildConfig.DEBUG) Timber.plant(PingleDebugTree())
    }

    private fun setDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun setNaverMap() {
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient(NAVER_MAP_CLIENT_ID)
    }
}
