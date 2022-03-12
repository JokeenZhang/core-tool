package com.zzq.core.tool.util

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager

object AppUtil {

    /**
     * 获取版本号。如果在build.gradle中versionName是1.0.0，那么就直接返回1.0.0
     */
    fun getVersionName(context: Context): String {
        val manager: PackageManager = context.packageManager
        val info: PackageInfo = manager.getPackageInfo(context.packageName, 0)
        val version = info.versionName
        return version
    }
}