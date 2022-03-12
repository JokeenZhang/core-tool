package com.zzq.core.tool.util

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {
    private val sFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    private val sDate = Date()

    fun format(time: Long): String {
        sDate.time = time
        return sFormat.format(sDate)
    }

    /**
     * 获取当前时间往前推一天的时间，与当前相隔一天的时间
     *
     * @return 返回当前24小时前的日期的毫秒数
     */
    fun dayStart(): Long {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        return calendar.timeInMillis
    }

    /**
     * 获取当前时间往前推一周的时间，与当前相隔一周的时间
     *
     * @return 返回上一周的日期的毫秒数
     */
    fun weekStart(): Long {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -7)
        return calendar.timeInMillis
    }

    /**
     * 获取当前时间往前推一个月的时间，与当前相隔一个月的时间
     *
     * @return 返回上一月的日期的毫秒数
     */
    fun monthStart(): Long {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -1)
        return calendar.timeInMillis
    }
}