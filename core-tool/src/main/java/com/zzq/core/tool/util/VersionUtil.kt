package com.zzq.core.tool.util

import android.content.Context

object VersionUtil {
    fun getVersion(context: Context): String {
        return try {
            val manager = context.packageManager
            val info = manager.getPackageInfo(context.packageName, 0)
            "v" + info.versionName
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}