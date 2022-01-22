package com.example.simpletimer.extension

object TimerConstants {
    const val MAX_VALUE = 999999L
    const val STRING_FORMAT = "%02d:%02d:%02d"
    const val REGEX_DIGIT = """[^0-9]"""
    const val DEFAULT_TIME = "00:00:00"

    const val MINUTES_IN_HOUR = 60
    const val SECS_IN_MINUTE = 60
    const val MSECS_IN_SEC = 1000
    const val COUNT_DOWN_IN_MSEC = 1000L
}

fun String?.toTimerLong(): Long {
    return this?.replace(TimerConstants.REGEX_DIGIT.toRegex(), "")?.toLong()?.coerceAtMost(TimerConstants.MAX_VALUE) ?: 0
}


// convert seconds to string 00:00:00
fun Long.toTimerString(): String {
    val (hours, minutes, seconds) = this.fromSecondsToTimerValue()
    return String.format(TimerConstants.STRING_FORMAT, hours, minutes, seconds)
}

fun Long.toMillisecond(): Long {
    val (hours, minutes, seconds) = this.toTimerValue()
    return (((hours * TimerConstants.MINUTES_IN_HOUR + minutes) * TimerConstants.SECS_IN_MINUTE) + seconds) * TimerConstants.MSECS_IN_SEC
}

private fun Long.toTimerValue(): Triple<Long, Long, Long> {
    var timeInt = this
    val seconds = timeInt % 100
    timeInt /= 100

    val minutes = timeInt % 100
    timeInt /= 100

    val hours = timeInt
    return Triple(hours, minutes, seconds)
}

private fun Long.fromSecondsToTimerValue(): Triple<Long, Long, Long> {
    val totalSeconds = this
    val seconds = totalSeconds % TimerConstants.SECS_IN_MINUTE

    val totalMinutes =  totalSeconds / TimerConstants.SECS_IN_MINUTE
    val minutes = totalMinutes % TimerConstants.MINUTES_IN_HOUR

    val hours = totalMinutes / TimerConstants.MINUTES_IN_HOUR
    return Triple(hours, minutes, seconds)
}
