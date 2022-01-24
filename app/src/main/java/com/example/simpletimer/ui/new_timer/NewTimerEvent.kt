package com.example.simpletimer.ui.new_timer

sealed class NewTimerEvent {
    object OnCancelClick: NewTimerEvent()
    object OnSaveTimerClick: NewTimerEvent()
    data class OnTimeChange(val time: String?): NewTimerEvent()
    data class OnLabelChange(val label: String?): NewTimerEvent()
}