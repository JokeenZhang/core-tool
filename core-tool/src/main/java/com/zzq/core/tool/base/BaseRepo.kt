package com.zzq.core.tool.base

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import com.zzq.core.tool.bean.ResponseMsgBean

open class BaseRepo(protected val responseMsgLiveData : MutableLiveData<ResponseMsgBean>) {

    protected fun getString(@StringRes idRes: Int): String {
        return BaseApplication.instance.getString(idRes)
    }
}