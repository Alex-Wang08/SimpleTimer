package com.example.simpletimer.ui.timer_add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletimer.data.TimerObject
import com.example.simpletimer.data.TimerRepository
import com.example.simpletimer.extension.*
import com.example.simpletimer.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class TimerAddViewModel @Inject constructor(
    private val repository: TimerRepository
) : ViewModel() {

    //region Variables
    var timerLabel by mutableStateOf("")
        private set

    var timeString by mutableStateOf(TimerConstants.DEFAULT_TIME_STRING)
        private set
    //endregion

    //region Events
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: TimerAddEvent) {
        when (event) {
            is TimerAddEvent.OnTimeChange -> {
                updateTimerValue(event.time)
            }

            is TimerAddEvent.OnLabelChange -> {
                updateTimerLabel(event.label)
            }

            is TimerAddEvent.OnCancelClick -> {
                sendUiEvent(UiEvent.PopBackStack)
            }

            is TimerAddEvent.OnSaveTimerClick -> {
                saveTimer()
            }
        }
    }
    //endregion

    // region Private Helpers
    private fun saveTimer() {
        if (timeString == TimerConstants.DEFAULT_TIME_STRING) {
            sendUiEvent(UiEvent.ShowToastMessage)
            return
        }

        viewModelScope.launch {
            // we may encounter minute or second is over 60, for example: 01:70:91, recalculate it to normal value as: 02:11:31
            val newTimeString = timeString.fromTimerStringToTimerLong().fromTimeLongToSeconds()
                .fromSecondsToTimerString()
            val label = if (timerLabel.isEmpty()) "Timer" else timerLabel

            val res = async {
                repository.insertTimer(
                    TimerObject(
                        label = label,
                        originalTime = newTimeString,
                        currentTime = newTimeString,
                        isRunning = true,
                        hasAutoStarted = false
                    )
                )
            }
            res.await()
            sendUiEvent(UiEvent.PopBackStack)
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

    private fun updateTimerLabel(label: String?) {
        if (timerLabel == label) return
        timerLabel = label ?: ""
    }
    //endregion
}