package com.zzq.core.tool.interfaces

import android.view.View

class ClickProxy(private val origin: View.OnClickListener) : View.OnClickListener {
    private var lastclick: Long = 0
    private val timems: Long = 1000

    override fun onClick(v: View) {
        if (System.currentTimeMillis() - lastclick >= timems) {
            origin.onClick(v)
            lastclick = System.currentTimeMillis()
        }
    }
}