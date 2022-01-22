package com.example.simpletimer.data

import kotlinx.coroutines.flow.Flow

class TimerRepositoryImpl(
    private val dao: TimerDao
) : TimerRepository {

    override suspend fun insertTimer(timer: Timer) {
        dao.insertTimer(timer)
    }

    override suspend fun deleteTimer(timer: Timer) {
        dao.deleteTimer(timer)
    }

    override suspend fun getTimerById(id: Int): Timer? {
        return dao.getTimerById(id)
    }

    override fun getTimerList(): Flow<List<Timer>> {
        return dao.getTimerList()
    }
}