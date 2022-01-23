package com.example.simpletimer.extension

import com.example.simpletimer.data.Timer
import com.example.simpletimer.data.TimerObject
import kotlin.math.ceil

object TimerConstants {
    const val MAX_VALUE = 999999L
    const val STRING_FORMAT = "%02d:%02d:%02d"
    const val REGEX_DIGIT = """[^0-9]"""
    const val DEFAULT_TIME_STRING = "00:00:00"
}

// 10:01:01 -> 100101
fun String?.fromTimerStringToTimerLong(): Long {
    return this?.replace(TimerConstants.REGEX_DIGIT.toRegex(), "")?.toLong() ?: 0L
}

// 00:01:01 -> 61000
fun String?.fromTimerStringToMilliseconds(): Long {
    return this?.fromTimerStringToTimerLong()?.fromTimerLongToMillisecond() ?: 0L
}

// 101 -> 61
fun Long.fromTimeLongToSeconds(): Long {
    val (hours, minutes, seconds) = this.fromTimerLongToTimerValue()
    return ((hours * 60 + minutes) * 60) + seconds
}

// 101 -> 61000
fun Long.fromTimerLongToMillisecond(): Long {
    return this.fromTimeLongToSeconds() * 1000
}

// 61 -> string 00:01:01
fun Long.fromSecondsToTimerString(): String {
    val (hours, minutes, seconds) = this.fromSecondsToTimerValue()
    return String.format(TimerConstants.STRING_FORMAT, hours, minutes, seconds)
}

// 61000 -> 00:01:01
fun Long.fromMillisecondsToTimerString(): String {
    return ceil(this / 1000.0).toLong().fromSecondsToTimerString()
}

// 101 -> 00:01:01
fun Long.fromTimerLongToTimerString(): String {
    val (hours, minutes, seconds) = this.fromTimerLongToTimerValue()
    return String.format(TimerConstants.STRING_FORMAT, hours, minutes, seconds)
}

// 101 -> (0, 1, 1)
private fun Long.fromTimerLongToTimerValue(): Triple<Long, Long, Long> {
    var timeInt = this
    val seconds = timeInt % 100
    timeInt /= 100

    val minutes = timeInt % 100
    timeInt /= 100

    val hours = timeInt
    return Triple(hours, minutes, seconds)
}

// 61 -> (0, 1, 1)
private fun Long.fromSecondsToTimerValue(): Triple<Long, Long, Long> {
    val totalSeconds = this
    val seconds = totalSeconds % 60

    val totalMinutes =  totalSeconds / 60
    val minutes = totalMinutes % 60

    val hours = totalMinutes / 60
    return Triple(hours, minutes, seconds)
}

// region mappers entity to object
fun Timer.toTimerObject(): TimerObject {
    return TimerObject(
        id = this.id,
        label = this.label,
        originalTime = this.originalTime,
        currentTime = this.originalTime,
        isRunning = true,
        hasAutoStarted = false
    )
}

fun TimerObject.toTimer(): Timer {
    return Timer(
        id = this.id,
        label = this.label,
        originalTime = this.originalTime
    )
}
//endregion
