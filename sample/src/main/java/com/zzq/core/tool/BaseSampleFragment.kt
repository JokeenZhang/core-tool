package com.zzq.core.tool

import androidx.annotation.LayoutRes
import com.zzq.core.tool.base.BaseFragment

abstract class BaseSampleFragment(@LayoutRes contentLayoutId:Int) : BaseFragment(contentLayoutId) {

    /**
     * 这里是演示项目，可能不会用到Toolbar，return null会更高概率的出现
     */
    override fun setToolbarId(): Int? {
        return null
    }
}