package com.example.simpletimer.util

sealed class UiEvent {
    object PopBackStack: UiEvent()
    data class Navigate(val route: String): UiEvent()
    data class SendNotification(val label: String) : UiEvent()
    object ShowToastMessage: UiEvent()
}