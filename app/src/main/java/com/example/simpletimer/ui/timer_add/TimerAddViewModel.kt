package com.example.simpletimer.ui.timer_add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletimer.data.Timer
import com.example.simpletimer.data.TimerRepository
import com.example.simpletimer.extension.*
import com.example.simpletimer.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerAddViewModel @Inject constructor(
    private val repository: TimerRepository
) : ViewModel() {

    var timeString by mutableStateOf(TimerConstants.DEFAULT_TIME)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: TimerAddEvent) {
        when (event) {
            is TimerAddEvent.OnTimeChange -> {
                updateTimerValue(event.time)
            }

            is TimerAddEvent.OnCancelClick -> {
                sendUiEvent(UiEvent.PopBackStack)
            }

            is TimerAddEvent.OnSaveTimerClick -> {
                saveTimer()

                /*todo: save time to database*/
                sendUiEvent(UiEvent.PopBackStack)
            }

        }
    }

    private fun saveTimer() {
        viewModelScope.launch {
            if (timeString == TimerConstants.DEFAULT_TIME) {
                sendUiEvent(UiEvent.ShowSnackBar(
                    message = "You need set up a time"
                ))
                return@launch
            }
            repository.insertTimer(
                Timer(
                    label = "Timer",
                    originalTime = timeString,
                    currentTime = timeString,
                    isRunning = true
                )
            )
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private fun updateTimerValue(value: String?) {
        if (value == null) return

        // timer string in format of 12:23:56
        val timeLong = value.fromTimerStringToTimerLong()
        if (timeLong > TimerConstants.MAX_VALUE) return
        timeString = timeLong.fromTimerLongToTimerString()
    }
}