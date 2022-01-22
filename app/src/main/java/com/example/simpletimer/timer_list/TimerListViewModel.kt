package com.example.simpletimer.timer_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletimer.data.Timer
import com.example.simpletimer.data.TimerRepository
import com.example.simpletimer.util.Routes
import com.example.simpletimer.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerListViewModel @Inject constructor(
//    private val repository: TimerRepository

): ViewModel() {
    var timers = ArrayList<Timer>()


    init {
        val timer1 =  Timer(
            label = "timer1",
            originalTime = 20,
            currentTime = 20,
            isTimerRunning = false
        )
        val timer2 = Timer(
            label = "timer1",
            originalTime = 20,
            currentTime = 20,
            isTimerRunning = false
        )
        val timer3 =  Timer(
            label = "timer1",
            originalTime = 20,
            currentTime = 20,
            isTimerRunning = false
        )
        val timer4 = Timer(
            label = "timer1",
            originalTime = 20,
            currentTime = 20,
            isTimerRunning = false
        )
        timers.add(timer1)
        timers.add(timer2)
        timers.add(timer3)
        timers.add(timer4)

    }








    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun onEvent(event: TimerListEvent) {
        when (event) {
            is TimerListEvent.OnAddTimerClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.TIMER_ADD))
            }
        }
    }


    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}