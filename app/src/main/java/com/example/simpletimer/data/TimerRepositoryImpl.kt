package com.example.simpletimer.data

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

    override suspend fun getTimerList(): List<Timer> {
        return dao.getTimerList()
    }
}