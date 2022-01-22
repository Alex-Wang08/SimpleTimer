package com.example.simpletimer.timer_list

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private val _timers = ArrayList<Timer>()
    val timersLiveData = mutableStateListOf<Timer>()

    init {
        val timer1 = Timer(
            id = "timer1",
            label = "timer1",
            originalTime = 20,
            currentTime = 20,
            isTimerRunning = true
        )
//        val timer2 = Timer(
//            id = "timer2",
//            label = "timer2",
//            originalTime = 20,
//            currentTime = 20,
//            isTimerRunning = false
//        )
//        val timer3 = Timer(
//            id = "timer3",
//            label = "timer3",
//            originalTime = 20,
//            currentTime = 20,
//            isTimerRunning = true
//        )
//        val timer4 = Timer(
//            id = "timer4",
//            label = "timer4",
//            originalTime = 20,
//            currentTime = 20,
//            isTimerRunning = false
//        )
        _timers.add(timer1)
//        _timers.add(timer2)
//        _timers.add(timer3)
//        _timers.add(timer4)
        timersLiveData.addAll(_timers)
    }








    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun onEvent(event: TimerListEvent) {
        when (event) {
            is TimerListEvent.OnAddTimerClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.TIMER_ADD))
            }
            is TimerListEvent.OnDeleteTimerClick -> {
                deleteTimer(event.timer, event.index)
            }
            is TimerListEvent.OnTimerStateChange -> {
                changeTimerState(event.timer, event.index)

            }
        }
    }

    private fun changeTimerState(timer: Timer, index: Int) {
        val isTimerRunning = timer.isTimerRunning
        timersLiveData[index] = timersLiveData[index].copy(isTimerRunning = !isTimerRunning)

        if (isTimerRunning) {
            cancelCountDown()
        } else {
            startCountDown()
        }
    }


    private fun startCountDown() {

    }


    private fun cancelCountDown() {

    }





    private fun deleteTimer(timer: Timer, index: Int) {
        timersLiveData.removeAt(index)
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}