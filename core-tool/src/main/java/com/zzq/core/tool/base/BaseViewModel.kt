package com.zzq.core.tool.base

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zzq.core.tool.bean.ResponseMsgBean

abstract class BaseViewModel<T : BaseRepo> : ViewModel() {

    /**
     * 可通用返回各类自定义消息
     */
    protected open var responseMsgLiveData = MutableLiveData<ResponseMsgBean>()

    protected var repo: T = initialRepo(responseMsgLiveData)

    abstract fun initialRepo(msgLiveData: MutableLiveData<ResponseMsgBean>): T

    fun getResponseMsgLiveData(): LiveData<ResponseMsgBean> {
        return responseMsgLiveData
    }

    protected fun getString(@StringRes idRes: Int): String {
        return BaseApplication.instance.getString(idRes)
    }
}