package com.example.simpletimer.timer_add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletimer.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerAddViewModel @Inject constructor() : ViewModel() {

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: TimerAddEvent) {
        when(event) {
            is TimerAddEvent.OnCancelClick -> {
                sendUiEvent(UiEvent.PopBackStack)
            }

            is TimerAddEvent.OnSaveTimerClick -> {

                /*todo: save time to database*/
                sendUiEvent(UiEvent.PopBackStack)
            }

        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}