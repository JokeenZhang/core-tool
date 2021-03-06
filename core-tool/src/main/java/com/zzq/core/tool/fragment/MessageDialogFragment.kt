package com.zzq.core.tool.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import com.zzq.core.tool.base.BaseApplication
import com.zzq.core.tool.base.BaseDialogFragment
import com.zzq.core.tool.interfaces.ClickProxy
import com.zzq.core.tool.interfaces.DialogCallback
import com.zzq.core.tool.R

open class MessageDialogFragment : BaseDialogFragment(),
    View.OnClickListener {
    private var mDialogCallback: DialogCallback? = null
    private lateinit var mTvDialogMessage: TextView
    private lateinit var mTvDialogTitle: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_fragment_message, container, false).apply {
            mTvDialogMessage = findViewById(R.id.tv_dialog_message)
            mTvDialogTitle = findViewById(R.id.tv_dialog_title)
            val tvDialogOk = findViewById<TextView>(R.id.tv_dialog_btn_ok)
            val tvDialogCancel = findViewById<TextView>(R.id.tv_dialog_btn_cancel)
            tvDialogOk.setOnClickListener(ClickProxy(this@MessageDialogFragment))
            tvDialogCancel.setOnClickListener(ClickProxy(this@MessageDialogFragment))
            val bundle = arguments
            if (bundle?.getString(TEXT_MESSAGE) != null) {
                mTvDialogMessage.text = bundle.getString(TEXT_MESSAGE)
            }
            if (bundle?.getString(TEXT_TITLE) != null) {
                mTvDialogTitle.text = bundle.getString(TEXT_TITLE)
            }
            if (bundle?.getString(BUTTON_TEXT_OK) != null) {
                tvDialogOk.text = bundle.getString(BUTTON_TEXT_OK)
            }
            if (bundle?.getString(BUTTON_TEXT_CANCEL) != null) {
                tvDialogCancel.text = bundle.getString(BUTTON_TEXT_CANCEL)
            }
            if (bundle?.getBoolean(BUTTON_INVISIBLE_CANCEL) != null) {
                tvDialogCancel.isVisible = !bundle.getBoolean(BUTTON_INVISIBLE_CANCEL)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        afterAction(view)
    }

    override fun onStart() {
        isOverride = false
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val window = dialog.window
            val params = window!!.attributes
            params.width = WindowManager.LayoutParams.MATCH_PARENT
            window.attributes = params
            val dm = DisplayMetrics()
            //??????????????????
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requireActivity().windowManager.defaultDisplay.getMetrics(dm)
            window.setLayout((dm.widthPixels * 0.75).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    fun setDialogCallback(dialogCallback: DialogCallback?) {
        mDialogCallback = dialogCallback
    }

    override fun onClick(v: View) {
        val id = v.id
        //??????????????????????????????????????????????????????????????????????????????Dialog???
        if (id == R.id.tv_dialog_btn_ok) {
            dismiss()
            mDialogCallback?.confirm()
        } else if (id == R.id.tv_dialog_btn_cancel) {
            dismiss()
            mDialogCallback?.cancel()
        }
    }

    companion object {
        private lateinit var instance: MessageDialogFragment
        private var isStrict = false

        @JvmStatic
        fun newInstance(message: String, title: String = ""): MessageDialogFragment {
            isStrict = true
            instance = MessageDialogFragment()
            val bundle = Bundle()
            bundle.putString(TEXT_MESSAGE, message)
            bundle.putString(TEXT_TITLE, title)
            instance.arguments = bundle
            return instance
        }

        /**
         * @param message Dialog??????????????????
         * @param title Dialog??????????????????
         * @param btnOkText Dialog????????????????????????
         * @param btnCancelText Dialog????????????????????????
         * @param action ??????????????????????????????View?????????????????????????????????
         */
        @JvmStatic
        fun newInstance(
            message: String,
            title: String = "",
            btnOkText: String? = null,
            btnCancelText: String? = null,
            action: (View) -> Unit = {}
        ): MessageDialogFragment {
            isStrict = true
            instance = MessageDialogFragment()
            val bundle = Bundle()
            bundle.putString(TEXT_MESSAGE, message)
            bundle.putString(TEXT_TITLE, title)
            bundle.putString(BUTTON_TEXT_OK, btnOkText)
            bundle.putString(BUTTON_TEXT_CANCEL, btnCancelText)
            instance.arguments = bundle
            afterAction = action
            return instance
        }

        /**
         * ??????????????????????????????
         *
         * @param message       ?????????????????????
         * @param isOnlyConfirm ?????????????????????????????????????????????????????????
         * @return
         */
        @JvmStatic
        fun newInstance(
            message: String,
            title: String = "",
            btnOkText: String = BaseApplication.instance.getString(R.string.ok),
            isOnlyConfirm: Boolean = false
        ): MessageDialogFragment {
            isStrict = true
            val bundle = Bundle()
            bundle.putString(TEXT_MESSAGE, message)
            bundle.putString(TEXT_TITLE, title)
            bundle.putString(BUTTON_TEXT_OK, btnOkText)
            bundle.putBoolean(BUTTON_INVISIBLE_CANCEL, isOnlyConfirm)
            val dialog = MessageDialogFragment()
            dialog.arguments = bundle
            return dialog
        }

        private var afterAction: (View) -> Unit = {}
        private const val TEXT_TITLE = "title"
        private const val TEXT_MESSAGE = "message"
        private const val BUTTON_TEXT_OK = "Button_Text_Ok"
        private const val BUTTON_TEXT_CANCEL = "Button_Text_cancel"
        /**????????????????????????*/
        private const val BUTTON_INVISIBLE_CANCEL = "isOnlyConfirm"
    }

    init {
        require(isStrict) { "?????????newInstance??????MessageDialogFragment???" }
    }
}