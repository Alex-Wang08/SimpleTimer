package com.example.simpletimer.ui.show_timer

import com.example.simpletimer.data.TimerObject

/***
 * all the events that will be triggered on the show timer screen
 */

sealed class ShowTimerEvent {
    data class OnDeleteTimerClick(val timer: TimerObject, val index: Int) : ShowTimerEvent()
    data class OnTimerStateChange(val timer: TimerObject, val index: Int) : ShowTimerEvent()
    data class OnAutoStartTimer(val timer: TimerObject, val index: Int) : ShowTimerEvent()
    object OnAddTimerClick : ShowTimerEvent()
}