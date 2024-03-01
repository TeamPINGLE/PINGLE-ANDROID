package org.sopt.pingle.presentation.ui.onboarding.onboardingexplanation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class OnboardingViewModel : ViewModel() {
    private val _currentPosition = MutableStateFlow(FIRST_POSITION)
    val currentPosition get() = _currentPosition.asStateFlow()

    fun setCurrentPosition(position: Int) {
        _currentPosition.value = position
    }

    companion object {
        private const val FIRST_POSITION = 0
    }
}
