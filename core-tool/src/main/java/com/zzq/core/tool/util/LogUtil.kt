package com.zzq.core.tool.util

import android.util.Log
import com.zzq.core.tool.BuildConfig
import com.zzq.core.tool.base.*

fun eLog(tag: String, msg: String) {
    if (BuildConfig.isDebug) {
        Log.e(tag, msg)
    }
}

fun dLog(tag: String, msg: String) {
    if (BuildConfig.isDebug) {
        Log.d(tag, msg)
    }
}

fun iLog(tag: String, msg: String) {
    Log.i(tag, msg)
}

/**************************************************************************************************/

fun BaseApplication.eLog(msg: String) {
    eLog(this.javaClass.simpleName, msg)
}

fun BaseActivity.eLog(msg: String) {
    eLog(this.javaClass.simpleName, msg)
}

fun BaseFragment.eLog(msg: String) {
    eLog(this.javaClass.simpleName, msg)
}

fun BaseViewModel<*>.eLog(msg: String) {
    eLog(this.javaClass.simpleName, msg)
}

fun BaseRepo.eLog(msg: String) {
    eLog(this.javaClass.simpleName, msg)
}

/**************************************************************************************************/

fun BaseApplication.dLog(msg: String) {
    dLog(this.javaClass.simpleName, msg)
}

fun BaseActivity.dLog(msg: String) {
    dLog(this.javaClass.simpleName, msg)
}

fun BaseFragment.dLog(msg: String) {
    dLog(this.javaClass.simpleName, msg)
}

fun BaseViewModel<*>.dLog(msg: String) {
    dLog(this.javaClass.simpleName, msg)
}

fun BaseRepo.dLog(msg: String) {
    dLog(this.javaClass.simpleName, msg)
}

/**************************************************************************************************/

fun BaseApplication.iLog(msg: String) {
    iLog(this.javaClass.simpleName, msg)
}

fun BaseActivity.iLog(msg: String) {
    iLog(this.javaClass.simpleName, msg)
}

fun BaseFragment.iLog(msg: String) {
    iLog(this.javaClass.simpleName, msg)
}

fun BaseViewModel<*>.iLog(msg: String) {
    iLog(this.javaClass.simpleName, msg)
}

fun BaseRepo.iLog(msg: String) {
    iLog(this.javaClass.simpleName, msg)
}