package org.sopt.pingle.util.activity

import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import org.sopt.pingle.R
import org.sopt.pingle.presentation.type.SnackbarType
import org.sopt.pingle.util.component.PingleSnackbar
import org.sopt.pingle.util.context.stringOf

fun AppCompatActivity.setDoubleBackPressToExit(view: View) {
    var backPressedTime = INIT_BACK_PRESSED_TIME

    onBackPressedDispatcher.addCallback(
        this,
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() - backPressedTime <= FINISH_INTERVAL_TIME) {
                    finish()
                } else {
                    backPressedTime = System.currentTimeMillis()
                    PingleSnackbar.makeSnackbar(
                        view = view,
                        message = stringOf(R.string.all_on_back_pressed_snackbar),
                        bottomMarin = SNACKBAR_BOTTOM_MARGIN,
                        snackbarType = SnackbarType.GUIDE
                    )
                }
            }
        }
    )
}

const val FINISH_INTERVAL_TIME = 2000
const val INIT_BACK_PRESSED_TIME = 0L
const val SNACKBAR_BOTTOM_MARGIN = 12
