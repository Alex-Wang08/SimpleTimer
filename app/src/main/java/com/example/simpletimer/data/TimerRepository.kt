package com.example.simpletimer.data

interface TimerRepository {
    suspend fun insertTimer(timer: Timer)
    suspend fun deleteTimer(timer: Timer)
    suspend fun getTimerById(id: Int): Timer?
    suspend fun getTimerList(): List<Timer>
}