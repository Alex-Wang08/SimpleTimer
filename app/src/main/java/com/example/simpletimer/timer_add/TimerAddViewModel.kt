package com.example.simpletimer.timer_add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _time = MutableLiveData("00:00:00")
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
        value?.let {
            // timer string in format of 12:23:56
            val timeInt = convertTimeStringToInt(it)
            if (timeInt > 999999) return

            val timeString = try {
                convertTimeIntToString(timeInt)
            } catch (e: Exception) {
                return
            }

            _time.postValue(timeString)
        }

    }

    private fun convertTimeStringToInt(time: String): Int {
        return time.replace("""[^0-9]""".toRegex(), "").toInt()
    }

    private fun convertTimeIntToString(time: Int = 0): String {
        var timeInt = time
        val seconds = timeInt % 100
        timeInt /= 100

        val minutes = timeInt % 100
        timeInt /= 100

        val hours = timeInt
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}