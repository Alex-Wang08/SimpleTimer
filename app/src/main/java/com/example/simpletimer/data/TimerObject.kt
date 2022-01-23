package com.example.simpletimer.data

data class TimerObject (
    val id: Int? = null,
    var label: String,
    var originalTime: String,
    var currentTime: String,
    var isRunning: Boolean = false,
    var hasAutoStarted: Boolean = false
)