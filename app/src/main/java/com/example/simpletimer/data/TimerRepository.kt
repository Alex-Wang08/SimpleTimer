package com.example.simpletimer.data

import kotlinx.coroutines.flow.Flow

interface TimerRepository {
    suspend fun insertTimer(timer: Timer)
    suspend fun deleteTimer(timer: Timer)
    suspend fun getTimerById(id: Int): Timer?
    fun getTimerList(): Flow<List<Timer>>
}