package com.guicarneirodev.taxiapp.presentation.base

sealed interface ViewState {
    data object Loading : ViewState
    data object Success : ViewState
    data class Error(val message: String) : ViewState
    data object Idle : ViewState
}