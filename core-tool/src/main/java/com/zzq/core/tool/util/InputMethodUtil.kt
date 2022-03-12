package com.zzq.core.tool.util

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.ComponentActivity

object InputMethodUtil {
    fun showSoftInput(context: Context, editText: EditText) {
        editText.isFocusable = true
        editText.isFocusableInTouchMode = true
        //请求获得焦点
        editText.requestFocus()
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(editText, 0)
    }

    fun hiddenSoftInput(activity: ComponentActivity) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
    }
}