package com.example.simpletimer.ui.timer_list

import com.example.simpletimer.data.Timer

sealed class TimerListEvent {
    data class OnDeleteTimerClick(val timer: Timer, val index: Int): TimerListEvent()
    data class OnTimerStateChange(val timer: Timer, val index: Int): TimerListEvent()
    data class OnAutoStartTimer(val timer: Timer, val index: Int): TimerListEvent()
    object OnAddTimerClick: TimerListEvent()
}