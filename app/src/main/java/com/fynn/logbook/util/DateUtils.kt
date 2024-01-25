package com.fynn.logbook.util

import android.util.Log
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit

object DateUtils {

    /**
     * 比较两个日期相减是否与指定时间的大小
     */
    fun campareLongDate(updateDate: Long, dayInterval:Int): Boolean {
        val currentCalendar = Calendar.getInstance()
        val updateCalendar = Calendar.getInstance()
        updateCalendar.time = Date(updateDate)
        val diff = currentCalendar.timeInMillis - updateCalendar.timeInMillis
        val interval =  TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS).toInt()
        return interval >= dayInterval
    }

}