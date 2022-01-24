package com.example.simpletimer.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Timer(
    @PrimaryKey val id: Int? = null,
    @ColumnInfo(name = "label") var label: String,
    @ColumnInfo(name = "original_time") var originalTime: String
)