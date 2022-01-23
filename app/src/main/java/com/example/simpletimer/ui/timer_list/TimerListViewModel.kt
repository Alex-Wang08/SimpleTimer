package com.example.simpletimer.ui.timer_list

import android.os.CountDownTimer
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletimer.data.TimerObject
import com.example.simpletimer.data.TimerRepository
import com.example.simpletimer.extension.*
import com.example.simpletimer.util.Routes
import com.example.simpletimer.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerListViewModel @Inject constructor(
    private val repository: TimerRepository
) : ViewModel() {

    val timersLiveData = mutableStateListOf<TimerObject>()

    private var countDownTimer: CountDownTimer? = null

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val timers = async { repository.getTimerList() }
            timersLiveData.addAll(timers.await())
        }
    }

    fun onEvent(event: TimerListEvent) {
        when (event) {
            is TimerListEvent.OnAddTimerClick -> {
                createNewTimer()

            }
            is TimerListEvent.OnDeleteTimerClick -> {
                deleteTimer(event.timer, event.index)
            }
            is TimerListEvent.OnTimerStateChange -> {
                changeTimerState(event.timer, event.index)
            }
            is TimerListEvent.OnAutoStartTimer -> {
                autoStartCountDown(event.timer, event.index)
            }
        }
    }

    private fun createNewTimer() {
        if (timersLiveData.size > 0) {
            sendUiEvent(UiEvent.ShowToastMessage)
        } else {
            sendUiEvent(UiEvent.Navigate(Routes.TIMER_ADD))
        }
    }

    private fun autoStartCountDown(timerObject: TimerObject, index: Int) {
        if (!timerObject.isRunning || timerObject.hasAutoStarted) return

        startCountDown(timerObject, index)
        timerObject.hasAutoStarted = true
    }

    private fun changeTimerState(timerObject: TimerObject, index: Int) {
//        viewModelScope.launch {
//            timer.isRunning = !timer.isRunning
//            repository.insertTimer(timer.copy(isRunning = timer.isRunning))
//
//            if (timer.isRunning) {
//                startCountDown(timer, index)
//            } else {
//                cancelCountDown(timer)
//            }
//        }

        val isRunning = !timerObject.isRunning
        timersLiveData[index] = timersLiveData[index].copy(isRunning = isRunning)

        if (isRunning) {
            startCountDown(timerObject, index)
        } else {
            cancelCountDown(timerObject)
        }
    }

    private fun startCountDown(timerObject: TimerObject, index: Int) {
        countDownTimer?.cancel()

        val totalTimeInMilliseconds = timerObject.currentTime.fromTimerStringToMilliseconds()

        countDownTimer = object : CountDownTimer(totalTimeInMilliseconds, 1000) {

            override fun onTick(milliSecs: Long) {
                updateCurrentTime(milliSecs, index)
            }

            override fun onFinish() {
                sendUiEvent(UiEvent.SendNotification)
            }
        }
        countDownTimer?.start()
    }


    private fun updateCurrentTime(milliSecs: Long, index: Int) {
//        viewModelScope.launch {
//            val timeString = milliSecs.fromMillisecondsToTimerString()
//            repository.insertTimer(timer.copy(currentTime = timeString))
//        }
        val timeString = milliSecs.fromMillisecondsToTimerString()
        timersLiveData[index] = timersLiveData[index].copy(currentTime = timeString)
    }

    private fun cancelCountDown(timerObject: TimerObject) {
        countDownTimer?.cancel()
    }

    private fun deleteTimer(timerObject: TimerObject, index: Int) {
        countDownTimer?.cancel()
        viewModelScope.launch {
            repository.deleteTimer(timerObject)
            timersLiveData.removeAt(index)
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}