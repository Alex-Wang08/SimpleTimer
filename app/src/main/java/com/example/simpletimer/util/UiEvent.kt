package com.example.simpletimer.util

sealed class UiEvent {
    object PopBackStack: UiEvent()
    data class Navigate(val route: String): UiEvent()
    object SendNotification : UiEvent()
    data class ShowSnackBar(
        val message: String,
    ): UiEvent()
}