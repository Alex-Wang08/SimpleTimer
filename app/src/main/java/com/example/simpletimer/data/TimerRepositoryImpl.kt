package com.example.simpletimer.data

import com.example.simpletimer.extension.*

class TimerRepositoryImpl(
    private val dao: TimerDao
) : TimerRepository {

    override suspend fun insertTimer(timerObject: TimerObject) {
        dao.insertTimer(timerObject.toTimer())
    }

    override suspend fun deleteTimer(timerObject: TimerObject) {
        dao.deleteTimer(timerObject.toTimer())
    }

    override suspend fun getTimerById(id: Int): TimerObject? {
        return dao.getTimerById(id)?.toTimerObject()
    }

    override suspend fun getTimerList(): List<TimerObject> {
        return dao.getTimerList().map { timer -> timer.toTimerObject() }
    }
}