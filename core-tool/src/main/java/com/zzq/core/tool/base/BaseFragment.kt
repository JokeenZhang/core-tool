package com.zzq.core.tool.base

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gyf.immersionbar.ImmersionBar
import com.zzq.core.tool.R
import com.zzq.core.tool.util.eLog

/**
 * androidx
 * ViewModel
 * ImmersionBar
 * Navigation
 */
abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    protected var defaultToolbar: Toolbar? = null

    protected open fun <T : BaseViewModel<out BaseRepo>> initViewModel(modelClass: Class<T>): T {
        return ViewModelProvider(requireActivity()).get(modelClass)
    }

    protected open fun <T : ViewModel> initViewModel(modelClass: Class<T>): T {
        return ViewModelProvider(requireActivity()).get(modelClass)
    }

    protected open fun initView(view: View) {}

    /**
     * 页面无[Toolbar]时，重写该方法，返回null，这里默认的id是toolbar，默认大多数情况下都会设置toolbar。
     */
    @IdRes
    @Nullable
    protected open fun setToolbarId(): Int? = R.id.toolbar

    /**
     * 固定传递View的方式仅能通过构造函数传递，子类不可重写该方法。
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        eLog("BaseFragment $this onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initImmersionBar()
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

    protected open fun initImmersionBar() {
        ImmersionBar.with(this)
            .statusBarDarkFont(true)
            .init()
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
}