package org.sopt.pingle.util.view

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    object Empty : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error<T>(val message: String?, val code: Int? = null, val data: T? = null) : UiState<T>()
}
