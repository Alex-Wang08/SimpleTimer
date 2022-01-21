package com.example.simpletimer.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity
data class Timer(
//    @PrimaryKey val id: Int? = null,
    var label: String = "Timer",
    var originalTime: Long,
    var currentTime: Long,
    var isTimerRunning: Boolean = false
)