package com.zzq.core.tool.util

import android.app.Application
import android.os.Handler
import android.widget.Toast
import androidx.annotation.StringRes

object ToastUtil {
    private var sToast: Toast? = null
    private val sHandler = Handler()

    /**
     * 可以在Application初始化就赋值
     */
    lateinit var app: Application

    @JvmStatic
    fun showToast(msg: String) {
        if (null == sToast) {
            sToast = Toast.makeText(app, msg, Toast.LENGTH_SHORT)
        } else {
            sToast!!.setText(msg)
        }
        sToast!!.cancel()
        sHandler.postDelayed({ sToast!!.show() }, 200)
    }

    @JvmStatic
    fun showToast(@StringRes resId: Int) {
        val msg = app.getString(resId)
        if (null == sToast) {
            sToast = Toast.makeText(app, msg, Toast.LENGTH_SHORT)
        } else {
            sToast!!.setText(msg)
        }
        sToast!!.cancel()
        sHandler.postDelayed({ sToast!!.show() }, 200)
    }

    @JvmStatic
    fun showLongToast(msg: String?) {
        if (null == sToast) {
            sToast = Toast.makeText(app, msg, Toast.LENGTH_LONG)
        } else {
            sToast!!.setText(msg)
        }
        sToast!!.cancel()
        sHandler.postDelayed({ sToast!!.show() }, 200)
    }
}