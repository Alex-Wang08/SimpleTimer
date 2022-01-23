package com.example.simpletimer.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TimerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimer(timer: Timer)

    @Delete
    suspend fun deleteTimer(timer: Timer)

    @Query("SELECT * FROM Timer WHERE id = :id")
    suspend fun getTimerById(id: Int): Timer?

    @Query("SELECT * FROM Timer")
    fun getTimerList(): List<Timer>
}