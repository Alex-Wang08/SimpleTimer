package com.example.simpletimer.ui.timer_list

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletimer.data.Timer
import com.example.simpletimer.data.TimerRepository
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
    private val repository: TimerRepository
) : ViewModel() {

    val timerList = repository.getTimerList()

    private var countDownTimer: CountDownTimer? = null

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
        viewModelScope.launch {
            val isRunning = !timer.isRunning
            repository.insertTimer(timer.copy(isRunning = isRunning))

            if (isRunning) {
                startCountDown(timer, index)
            } else {
                cancelCountDown(timer)
            }
        }
    }

    private fun startCountDown(timer: Timer, index: Int) {
        val totalTimeInMilliseconds = timer.currentTime.fromTimerStringToMilliseconds()

        countDownTimer = object : CountDownTimer(totalTimeInMilliseconds, 1000) {

            override fun onTick(milliSecs: Long) {
                updateCurrentTime(milliSecs, timer)
            }

            override fun onFinish() {
                TODO("Not yet implemented")
            }
        }
        countDownTimer?.start()
    }

    private fun updateCurrentTime(milliSecs: Long, timer: Timer) {
        viewModelScope.launch {
            val timeString = milliSecs.fromMillisecondsToTimerString()
            repository.insertTimer(timer.copy(currentTime = timeString))
        }
    }

    private fun cancelCountDown(timer: Timer) {
        countDownTimer?.cancel()
    }

    private fun deleteTimer(timer: Timer, index: Int) {
        viewModelScope.launch {
            repository.deleteTimer(timer)
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}