package com.example.simpletimer.ui.timer_add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletimer.extension.*
import com.example.simpletimer.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerAddViewModel @Inject constructor(

) : ViewModel() {

    private val _time = MutableLiveData(TimerConstants.DEFAULT_TIME)
    val time: LiveData<String>
        get() = _time


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

    private fun updateTimerValue(value: String?) {
        if (value == null) return

        // timer string in format of 12:23:56
        val timeLong = value.fromTimerStringToTimerLong()
        if (timeLong > TimerConstants.MAX_VALUE) return
        _time.postValue(timeLong.fromTimerLongToTimerString())
    }
}