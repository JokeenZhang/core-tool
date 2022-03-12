package com.zzq.core.tool.util

import android.os.Build
import android.os.LocaleList
import java.util.*

object LanguageUtil {

    fun isZh(): Boolean {
        val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList.getAdjustedDefault()[0]
        } else {
            Locale.getDefault()
        }
        val language = locale.language
        return language != null && language.contains("zh")
    }
}
