package com.zzq.core.tool.interfaces

abstract class OnSelectCallback {
    abstract fun onPositive(msg: String? = null)
    open fun onNegative() {}
}