package com.zzq.core.tool.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.zzq.core.tool.R

open class BaseDialogFragment : DialogFragment() {
    var isCanceledOutside = true
    var widthScale = 0.75
    var isBackgroundTransparent = true
    var isOverride = true

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.Theme_Holo_Light_Dialog_MinWidth)
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        setStyle(DialogFragment.STYLE_NO_TITLE, com.google.android.material.R.style.Theme_AppCompat_Dialog_MinWidth);
        setStyle(
                STYLE_NO_TITLE,
//                R.style.Theme_MaterialComponents_Dialog_MinWidth
                com.google.android.material.R.style.Theme_MaterialComponents_Dialog_MinWidth
        )
    }

    protected fun <T : BaseViewModel<out BaseRepo>> initViewModel(modelClass: Class<T>): T {
        return ViewModelProvider(requireActivity()).get(modelClass)
    }

    override fun onStart() {
        super.onStart()
        if (!isOverride) {
            return
        }
        dialog?.let {
            it.setCancelable(isCanceledOutside)
            it.setCanceledOnTouchOutside(isCanceledOutside)
            //设置背景透明
            it.window?.let { window ->
                if (isBackgroundTransparent) {
                    window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                }
                val dm = DisplayMetrics()
                requireActivity().windowManager.defaultDisplay.getMetrics(dm)
                window.setLayout((dm.widthPixels * widthScale).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
            }
        }
    }

    override fun dismiss() {
        if (dialog != null && dialog!!.isShowing) {
            super.dismiss()
        }
    }
}