package com.example.simpletimer.data

interface TimerRepository {
    suspend fun insertTimer(timerObject: TimerObject)
    suspend fun deleteTimer(timerObject: TimerObject)
    suspend fun getTimerById(id: Int): TimerObject?
    suspend fun getTimerList(): List<TimerObject>
}