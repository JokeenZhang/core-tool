package com.zzq.core.tool.util

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

object ColorUtil {

    /**
     * 获取颜色，调用方式如ColorUtil.getColor(context, R.color.white)
     */
    @ColorInt
    fun getColor(context: Context, @ColorRes id: Int): Int {
        return ContextCompat.getColor(context, id)
    }
}