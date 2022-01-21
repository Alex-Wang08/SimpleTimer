package com.example.simpletimer.timer_add

sealed class TimerAddEvent {
    object OnCancelClick: TimerAddEvent()
    object OnSaveTimerClick: TimerAddEvent()
}