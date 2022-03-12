package com.zzq.core.tool.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorInt
import com.zzq.core.tool.base.BaseDialogFragment
import com.zzq.core.tool.interfaces.ClickProxy
import com.zzq.core.tool.interfaces.OnSelectCallback
import com.zzq.core.tool.R

open class CommonDialogFragment : BaseDialogFragment() {
    /*public CommonDialogFragment() throws UnsupportedOperationException{
        throw new UnsupportedOperationException("unsupported new instance for CommonDialogFragment!");
    }*/
    private lateinit var negativeText: String
    private lateinit var positiveText: String

    private var mOnSelectCallback: OnSelectCallback? = null

    @ColorInt
    private var okTextColor = Color.parseColor("#5bd4e7")

    @ColorInt
    private var cancelTextColor = Color.parseColor("#333333")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bundle = arguments ?: throw IllegalArgumentException("未使用newInstance方法创建实例！")
        val view = inflater.inflate(R.layout.dialog_common, container, false)
        val tvCancel = view.findViewById<TextView>(R.id.tv_cancel)
        val tvOk = view.findViewById<TextView>(R.id.tv_ok)
        val tvMessage = view.findViewById<TextView>(R.id.tv_message)
        val viewLine3 = view.findViewById<View>(R.id.view_line_3)
        if (bundle.getString(Message) != null) {
            tvMessage.text = bundle.getString(Message)
        }
        if (bundle.getBoolean("isOnlyConfirm")) {
            tvCancel.visibility = View.GONE
            viewLine3.visibility = View.GONE
        }
        tvCancel.setOnClickListener(ClickProxy(View.OnClickListener {
            mOnSelectCallback?.onNegative()
        }))
        tvCancel.setTextColor(cancelTextColor)
        tvOk.setOnClickListener(ClickProxy(View.OnClickListener {
            mOnSelectCallback?.onPositive("")
        }))
        tvOk.setTextColor(okTextColor)
        return view
    }

    override fun onStart() {
        super.onStart()
    }

    fun setOkTextColor(@ColorInt color: Int) {
        okTextColor = color
    }

    fun setCancelTextColor(@ColorInt color: Int) {
        cancelTextColor = color
    }

    fun setOnSelectCallback(onSelectCallback: OnSelectCallback?) {
        mOnSelectCallback = onSelectCallback
    }

    companion object {
        const val Message = "message"

        @JvmStatic
        fun newInstance(message: String?): CommonDialogFragment {
            val bundle = Bundle()
            bundle.putString(Message, message)
            bundle.putBoolean("isOnlyConfirm", false)
            val dialog = CommonDialogFragment()
            dialog.arguments = bundle
            return dialog
        }

        /**
         * 是否隐藏“取消”按钮
         *
         * @param message       弹窗显示的信息
         * @param isOnlyConfirm 是否隐藏？，调用该方法就直接认为隐藏了
         * @return
         */
        @JvmStatic
        fun newInstance(message: String?, isOnlyConfirm: Boolean): CommonDialogFragment {
            val bundle = Bundle()
            bundle.putString(Message, message)
            bundle.putBoolean("isOnlyConfirm", isOnlyConfirm)
            val dialog = CommonDialogFragment()
            dialog.arguments = bundle
            return dialog
        }
    }
}