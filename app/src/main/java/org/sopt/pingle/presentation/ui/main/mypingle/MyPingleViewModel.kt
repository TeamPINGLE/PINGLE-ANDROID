package org.sopt.pingle.presentation.ui.main.mypingle

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.sopt.pingle.data.datasource.local.PingleLocalDataSource
import javax.inject.Inject

@HiltViewModel
class MyPingleViewModel @Inject constructor(
    private val localStorage: PingleLocalDataSource,

) : ViewModel() {


}