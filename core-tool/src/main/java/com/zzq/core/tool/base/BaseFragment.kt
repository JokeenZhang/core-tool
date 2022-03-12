package com.zzq.core.tool.base

import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.annotation.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.components.SimpleImmersionOwner
import com.gyf.immersionbar.components.SimpleImmersionProxy
import com.zzq.core.tool.R

/**
 * androidx
 * ViewModel
 * ImmersionBar
 * Navigation
 */
abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId),
    SimpleImmersionOwner {

    protected var defaultToolbar: Toolbar? = null

    protected open fun <T : BaseViewModel<out BaseRepo>> initViewModel(modelClass: Class<T>): T {
        return ViewModelProvider(requireActivity()).get(modelClass)
    }

    protected open fun <T : ViewModel> initViewModel(modelClass: Class<T>): T {
        return ViewModelProvider(requireActivity()).get(modelClass)
    }

    protected open fun initView(view: View) {}

    /**
     * 页面无[Toolbar]时，重写该方法，返回null
     */
    @IdRes
    @Nullable
    protected open fun setToolbarId(): Int? = R.id.toolbar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbarId = setToolbarId()
        toolbarId?.let {
            defaultToolbar = view.findViewById(it)
            setToolbar(defaultToolbar!!)

        }
        initView(view)
    }

    protected open fun setToolbarReturnFun(function: () -> Unit, @DrawableRes iconId: Int? = null) {
//        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowTitleEnabled(false)
            if (iconId != null) {
                it.setHomeAsUpIndicator(iconId)
            }
            defaultToolbar?.setNavigationOnClickListener { function() }
        }
    }

    private fun setToolbar(toolbar: Toolbar) {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        ImmersionBar.setTitleBar(this, toolbar)
    }

    override fun initImmersionBar() {
        val immersionBar = ImmersionBar.with(this)
        if (ImmersionBar.isSupportStatusBarDarkFont()) {
            immersionBar.statusBarDarkFont(true)
        }
        immersionBar.init();
    }

    /**
     * 获取颜色
     * @return 返回颜色值，该值可用于TextView.setTextColor等场景
     */
    @ColorInt
    protected fun getColor(@ColorRes id: Int): Int {
        return ContextCompat.getColor(requireContext(), id)
    }

    protected fun getDrawable(@DrawableRes id: Int): Drawable? {
        return ContextCompat.getDrawable(requireContext(), id)
    }

    /**
     * ImmersionBar代理类
     */
    private val mSimpleImmersionProxy = SimpleImmersionProxy(this)
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mSimpleImmersionProxy.isUserVisibleHint = isVisibleToUser
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mSimpleImmersionProxy.onActivityCreated(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mSimpleImmersionProxy.onDestroy()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        mSimpleImmersionProxy.onHiddenChanged(hidden)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mSimpleImmersionProxy.onConfigurationChanged(newConfig)
    }

    /**
     * 是否可以实现沉浸式，当为true的时候才可以执行initImmersionBar方法
     * Immersion bar enabled boolean.
     *
     * @return the boolean
     */
    override fun immersionBarEnabled(): Boolean {
        return true
    }
}