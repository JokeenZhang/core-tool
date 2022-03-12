package com.zzq.core.tool.interfaces

abstract class DialogCallback {
    abstract fun confirm()
    open fun cancel(){}
}