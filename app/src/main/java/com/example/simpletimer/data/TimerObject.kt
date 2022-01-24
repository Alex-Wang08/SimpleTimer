package com.example.simpletimer.data

/***
 * this data object is used to map the time entity
 */

data class TimerObject(
    val id: Int? = null,
    var label: String,
    var originalTime: String,
    var currentTime: String,
    var isRunning: Boolean = false,
    var hasAutoStarted: Boolean = false
)