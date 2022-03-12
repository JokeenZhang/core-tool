package com.zzq.core.tool.base

import android.app.Application
import com.zzq.core.tool.util.ToastUtil

open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ToastUtil.app = this
        instance = this
    }

    companion object {
        lateinit var instance: BaseApplication
    }
}