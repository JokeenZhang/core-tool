package com.zzq.core.tool.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.InputFilter
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.fragment.app.FragmentTransaction
import com.zzq.core.tool.base.BaseDialogFragment
import com.zzq.core.tool.interfaces.ClickProxy
import com.zzq.core.tool.interfaces.OnSelectCallback
import com.zzq.core.tool.R
import java.util.regex.Pattern

class InputMessageDialogFragment : BaseDialogFragment(), View.OnClickListener {
    private var mOnSelectCallback: OnSelectCallback? = null
    private lateinit var mEtInputMessage: EditText
    private var mInputFilters = arrayOfNulls<InputFilter>(0)
    private var alarmText: String? = null
    private var titleText: String? = null
    private var inputLength = 0

    @ColorInt
    private var cancelTextColor = Color.parseColor("#333333")

    @ColorInt
    private var okTextColor = Color.parseColor("#00bcd4")
    override fun onStart() {
//        isBackgroundTransparent = false
        isOverride = false
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
            val dm = DisplayMetrics()
            //设置背景透明
            val window = dialog.window
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requireActivity().windowManager.defaultDisplay.getMetrics(dm)
            window.setLayout((dm.widthPixels * 0.75).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_fragment_input_message, container, false)
        val bundle = arguments ?: throw IllegalArgumentException("未使用newInstance方法创建实例！")
        val tvTitle = view.findViewById<TextView>(R.id.tv_dialog_title)
        val tvOk = view.findViewById<TextView>(R.id.tv_dialog_input_ok)
        tvOk.setTextColor(okTextColor)
        val tvCancel = view.findViewById<TextView>(R.id.tv_dialog_input_cancel)
        tvCancel.setTextColor(cancelTextColor)
        val tvAlarmText = view.findViewById<TextView>(R.id.tv_alarm_text)
        mEtInputMessage = view.findViewById(R.id.et_dialog_input)
        mEtInputMessage.setText("")
        //20200428 禁止输入换行符 更换，原来是通过InputFilter
        mEtInputMessage.setOnEditorActionListener { _, _, event ->
            event.keyCode == KeyEvent.KEYCODE_ENTER
        }
        if (mInputFilters.size != 0) {
            mEtInputMessage.setFilters(mInputFilters)
        }
        if (TextUtils.isEmpty(alarmText)) {
            tvAlarmText.visibility = View.GONE
        } else {
            tvAlarmText.visibility = View.VISIBLE
            tvAlarmText.text = alarmText
        }

        val text = bundle.getString(TEXT)
        val hintText = bundle.getString(HINT)
        titleText = bundle.getString(TITLE)
        if (!TextUtils.isEmpty(titleText)) {
            tvTitle.text = titleText
        } else {
            tvTitle.visibility = View.GONE
        }
        if (!TextUtils.isEmpty(hintText)) {
            mEtInputMessage.setHint(hintText)
        }
        if (!TextUtils.isEmpty(text)) {
            mEtInputMessage.setText(text)
            //TODO getText instaceOf text
            if (inputLength <= text!!.length) {
                mEtInputMessage.setSelection(inputLength)
            } else {
                mEtInputMessage.setSelection(text.length)
            }
        }
        tvCancel.setOnClickListener(ClickProxy(this))
        tvOk.setOnClickListener(ClickProxy(this))
        return view
    }

    fun setOnSelectCallback(onSelectCallback: OnSelectCallback?): InputMessageDialogFragment {
        mOnSelectCallback = onSelectCallback
        return this
    }

    fun setInputFilter(specialChat: String) {
        val filter = InputFilter { source, start, end, dest, dstart, dend ->
            //                String speChat="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
//                String speChat="[:/?*？：]";
            val pattern = Pattern.compile(specialChat)
            val matcher = pattern.matcher(source.toString())
            if (matcher.find()) {
                ""
            } else {
                null
            }
            //                return source.toString().replace("\n", "");
        }
        addInputFilter(filter)
    }

    /**
     * 设置输入文字长度，但每个Dialog仅能调用一次，或者以设置的最短长度为基准(特指每个DialogFragment实例）
     *
     * @param length 要设置的文字长度
     */
    fun setInputLength(length: Int): InputMessageDialogFragment {
        //用InputFilter来限制输入长度
        inputLength = length
        addInputFilter(InputFilter.LengthFilter(length))
        return this
    }

    private fun addInputFilter(inputFilter: InputFilter) {
        val newInputFilters = arrayOfNulls<InputFilter>(mInputFilters.size + 1)
        System.arraycopy(mInputFilters, 0, newInputFilters, 0, mInputFilters.size)
        newInputFilters[newInputFilters.size - 1] = inputFilter
        mInputFilters = newInputFilters
    }

    fun setAlarmText(text: String?): InputMessageDialogFragment {
        alarmText = text
        return this
    }

    fun setOkTextColor(@ColorInt color: Int): InputMessageDialogFragment {
        okTextColor = color
        return this
    }

    fun setCancelTextColor(@ColorInt color: Int): InputMessageDialogFragment {
        cancelTextColor = color
        return this
    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == R.id.tv_dialog_input_cancel) {
            if (mOnSelectCallback != null) {
                mOnSelectCallback!!.onNegative()
            }
        } else if (id == R.id.tv_dialog_input_ok) {
            val input = mEtInputMessage!!.text.toString()
            if (mOnSelectCallback != null) {
                mOnSelectCallback!!.onPositive(input)
            }
        }
    }

    override fun show(transaction: FragmentTransaction, tag: String?): Int {
        return super.show(transaction, tag)
    }

    override fun dismiss() {
        if (dialog != null && dialog!!.isShowing) {
            super.dismiss()
        }
    }

    companion object {
        const val TEXT = "text"
        const val HINT = "hintText"
        const val TITLE = "title"

        /**
         * 禁止输入换行符 废弃！
         * 原因：在华为，包括荣耀等部分机型在Android 10系统出现了输入"mi"字符后，出现的字符错乱，并且难以删除已经显示到
         * EditText上的内容，后面直接采用setOnEditorActionListener方式来处理该DialogFragment禁止换行问题
         *
         * @since 20200428
         *
         *
         * 如上来自MSensor项目
         */
        //    private InputFilter noHasNewLineFilter = (source, start, end, dest, dstart, dend) -> source.toString().replace("\n", "");
        fun newInstance(): InputMessageDialogFragment {
            val bundle = Bundle()
            bundle.putString(TEXT, "")
            bundle.putString(HINT, "")
            val dialog = InputMessageDialogFragment()
            dialog.arguments = bundle
            return dialog
        }

        @JvmStatic
        fun newInstance(
            titleText: String = "",
            text: String? = null,
            hintText: String?
        ): InputMessageDialogFragment {
            val bundle = Bundle()
            bundle.putString(TITLE, titleText)
            bundle.putString(TEXT, text ?: "")
            bundle.putString(HINT, hintText)
            val dialog = InputMessageDialogFragment()
            dialog.arguments = bundle
            return dialog
        }
    }
}