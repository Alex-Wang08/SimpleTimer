package com.example.simpletimer.ui.timer_list

import android.os.CountDownTimer
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletimer.data.Timer
import com.example.simpletimer.extension.*
import com.example.simpletimer.util.Routes
import com.example.simpletimer.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerListViewModel @Inject constructor(
//    private val repository: TimerRepository

) : ViewModel() {

    private var countDownTimer: CountDownTimer? = null
//    private var countDownTimerList = ArrayList<CountDownTimer>()

    private val _timers = ArrayList<Timer>()
    val timersLiveData = mutableStateListOf<Timer>()

    init {
        val timer1 = Timer(
            id = "timer1",
            label = "timer1",
            originalTime = "00:20:21",
            currentTime = "00:10:10",
            isTimerRunning = false
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


    private val _uiEvent = Channel<UiEvent>()
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
        val isTimerRunning = !timer.isTimerRunning
        timersLiveData[index] = timersLiveData[index].copy(isTimerRunning = isTimerRunning)

        if (isTimerRunning) {
            startCountDown(timer, index)
        } else {
            cancelCountDown(timer)
        }
    }

    private fun startCountDown(timer: Timer, index: Int) {
        val totalTimeInMilliseconds = timer.currentTime.fromTimerStringToMilliseconds()

        countDownTimer = object : CountDownTimer(totalTimeInMilliseconds, 1000) {

            override fun onTick(milliSecs: Long) {
                updateCurrentTime(milliSecs, index)
            }

            override fun onFinish() {
                TODO("Not yet implemented")
            }
        }
        countDownTimer?.start()
    }

    private fun updateCurrentTime(milliSecs: Long, index: Int) {
        val timeString = milliSecs.fromMillisecondsToTimerString()
        timersLiveData[index] = timersLiveData[index].copy(currentTime = timeString)
    }

    private fun cancelCountDown(timer: Timer) {
        countDownTimer?.cancel()
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