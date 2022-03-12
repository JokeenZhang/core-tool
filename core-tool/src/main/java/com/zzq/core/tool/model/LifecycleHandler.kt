package com.zzq.core.tool.model

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

/**
 * Handler子类，自动在 onDestroy 里移除消息，防止内存泄漏
 */
class LifecycleHandler(private val owner: LifecycleOwner) :
    Handler(Looper.getMainLooper()), LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun clearMessage() {
        removeCallbacksAndMessages(null)
        owner.lifecycle.removeObserver(this)
    }

    init {
        owner.lifecycle.addObserver(this)
    }
}