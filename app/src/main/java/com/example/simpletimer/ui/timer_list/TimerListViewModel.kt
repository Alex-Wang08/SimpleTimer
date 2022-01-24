package com.example.simpletimer.ui.timer_list

import android.os.CountDownTimer
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletimer.MainViewModel
import com.example.simpletimer.data.TimerObject
import com.example.simpletimer.data.TimerRepository
import com.example.simpletimer.extension.fromMillisecondsToTimerString
import com.example.simpletimer.extension.fromTimerStringToMilliseconds
import com.example.simpletimer.util.Routes
import com.example.simpletimer.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TimerListViewModel @Inject constructor(
    private val repository: TimerRepository,
    private val mainViewModel: MainViewModel
) : ViewModel() {

    //region Variables
    val timersLiveData = mutableStateListOf<TimerObject>()
    lateinit var timers: List<TimerObject>
    private var countDownTimer: CountDownTimer? = null
    private var isRefreshing: Boolean = false
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    var isFirstLoad: Boolean = true
    //endregion

    //region Event
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
            is TimerListEvent.OnRefreshList -> {
                refreshTimerList()
            }
        }
    }
    //endregion

    // region Private Helpers
    fun loadTimerList(isRefresh: Boolean = false) {
        if (!isRefresh && !isFirstLoad) return

        viewModelScope.launch(Dispatchers.IO) {
            isRefreshing = true
            timers = withContext(Dispatchers.Default) { repository.getTimerList() }
            timers.forEach { it.isRunning = isRefresh }

            mainViewModel.hasDatasetChanged = false
            timersLiveData.clear()
            timersLiveData.addAll(timers)

            isRefreshing = false
            isFirstLoad = false
        }
    }

    private fun refreshTimerList() {
        if (!mainViewModel.hasDatasetChanged || isRefreshing) return
        loadTimerList(true)
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
        val isRunning = !timerObject.isRunning
        timersLiveData[index] = timersLiveData[index].copy(isRunning = isRunning)

        if (isRunning) {
            startCountDown(timerObject, index)
        } else {
            cancelCountDown()
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
                sendUiEvent(UiEvent.SendNotification(timerObject.label))
                resetTimer(index)
            }
        }
        countDownTimer?.start()
    }

    private fun resetTimer(index: Int) {
        val originalTime = timersLiveData[index].originalTime
        val newTimer = timersLiveData[index].copy(currentTime = originalTime, isRunning = false)
        viewModelScope.launch {
            val res = async {
                repository.insertTimer(newTimer)
            }
            res.await()
            timersLiveData[index] = newTimer
        }
    }

    private fun updateCurrentTime(milliSecs: Long, index: Int) {
        val timeString = milliSecs.fromMillisecondsToTimerString()
        timersLiveData[index] = timersLiveData[index].copy(currentTime = timeString)
    }

    private fun cancelCountDown() {
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
    // endregion
}