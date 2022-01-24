package com.example.simpletimer.ui.timer_list

import com.example.simpletimer.data.TimerObject

sealed class TimerListEvent {
    data class OnDeleteTimerClick(val timer: TimerObject, val index: Int): TimerListEvent()
    data class OnTimerStateChange(val timer: TimerObject, val index: Int): TimerListEvent()
    data class OnAutoStartTimer(val timer: TimerObject, val index: Int): TimerListEvent()
    object OnAddTimerClick: TimerListEvent()
}