package com.example.simpletimer.timer_list

import com.example.simpletimer.data.Timer

sealed class TimerListEvent {
    data class OnDeleteTimerClick(val timer: Timer): TimerListEvent()
    data class OnTimerStateChange(val timer: Timer): TimerListEvent()
    object OnAddTimerClick: TimerListEvent()
}