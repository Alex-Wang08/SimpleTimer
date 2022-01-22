package com.example.simpletimer.ui.timer_add

sealed class TimerAddEvent {
    object OnCancelClick: TimerAddEvent()
    object OnSaveTimerClick: TimerAddEvent()
    data class OnTimeChange(val time: String?): TimerAddEvent()
}