package com.example.simpletimer.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Timer(
    @PrimaryKey val id: Int,
    @ColumnInfo(name= "label") var label: String,
    @ColumnInfo(name="original_time") var originalTime: String,
    @ColumnInfo(name = "current_time") var currentTime: String,
    @ColumnInfo(name = "is_running")var isRunning: Boolean = false
)