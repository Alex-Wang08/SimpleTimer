package com.example.simpletimer.ui.show_timer

import com.example.simpletimer.data.TimerObject

sealed class ShowTimerEvent {
    data class OnDeleteTimerClick(val timer: TimerObject, val index: Int): ShowTimerEvent()
    data class OnTimerStateChange(val timer: TimerObject, val index: Int): ShowTimerEvent()
    data class OnAutoStartTimer(val timer: TimerObject, val index: Int): ShowTimerEvent()
    object OnAddTimerClick: ShowTimerEvent()
}