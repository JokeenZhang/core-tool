package com.zzq.core.tool.base

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gyf.immersionbar.ImmersionBar
import com.zzq.core.tool.R

/**
 * 注意，各仓库应该自己实现BaseXXXActivity（继承[BaseActivity]），其仓库所有的Activity再继承这个类。而不是
 * 所有的Activity就直接继承与[BaseActivity]！
 */
open class BaseActivity : AppCompatActivity() {

    protected open fun initStatusBar() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.white)
                .init()
    }

    protected open fun <T : ViewModel> initViewModel(modelClass: Class<T>): T {
        return ViewModelProvider(this).get(modelClass)
    }

    /**
     * 设置Toolbar返回键关闭Activity
     * 注意：getSupportActionBar操作需要放在调用本方法之后，如setTitle()
     *
     * @param toolbar
     */
    protected open fun setToolbarReturnFun(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true)
            ab.setDisplayShowTitleEnabled(false)
            toolbar.setNavigationOnClickListener { returnAction() }
        }
    }

    protected open fun returnAction() {
        finish()
    }
}