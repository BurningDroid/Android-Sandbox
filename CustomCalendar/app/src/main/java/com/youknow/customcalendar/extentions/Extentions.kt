package com.youknow.customcalendar.extentions

import java.util.*
import java.util.Calendar.*

fun Long.month(): Long {
    val calendar = getInstance()
    calendar.timeInMillis = this
    return calendar.get(MONTH).toLong()
}

fun Long.getDayOfMonth(): String {
    val calendar = getInstance().apply { timeInMillis = this@getDayOfMonth }
    return calendar.get(DAY_OF_MONTH).toString()
}

fun Long.getDateList(): List<Long> {
    val months = ArrayList<Long>()
    val calendar = getInstance()
    calendar.timeInMillis = this
    calendar.set(DATE, 1)
    calendar.set(HOUR_OF_DAY, 6)
    calendar.set(MINUTE, 0)
    calendar.set(SECOND, 0)
    calendar.set(MILLISECOND, 0)

    when (calendar.get(DAY_OF_WEEK)) {
        MONDAY -> calendar.add(DATE, -1)
        TUESDAY -> calendar.add(DATE, -2)
        WEDNESDAY -> calendar.add(DATE, -3)
        THURSDAY -> calendar.add(DATE, -4)
        FRIDAY -> calendar.add(DATE, -5)
        SATURDAY -> calendar.add(DATE, -6)
        else -> {
        }
    }

    for (i in 0..41) {
        months.add(calendar.timeInMillis)
        calendar.add(DATE, 1)
    }

    return months
}
