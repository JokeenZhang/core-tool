package com.zzq.core.tool.ui

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.zzq.core.tool.base.BaseApplication
import com.zzq.core.tool.util.DensityUtil

class LinearItemDecoration constructor(private val marginDp: Float = 0.67F) :
    RecyclerView.ItemDecoration() {

    private var shouldDrawDecoration = false
    private val paint = Paint()

    /*constructor(marginDp: Float = 0.67F, setPaintParam: (Paint) -> Unit) : this(marginDp) {
        shouldDrawDecoration = true
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.color = ContextCompat.getColor(BaseApplication.instance, R.color.default_background)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = marginDp
        setPaintParam(paint)
    }*/

    constructor(marginDp: Float = 0.67F, @ColorRes lineColor: Int) : this(marginDp) {
        shouldDrawDecoration = true
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.color = ContextCompat.getColor(BaseApplication.instance, lineColor)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = marginDp
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = DensityUtil.dip2px(BaseApplication.instance, marginDp)
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(canvas, parent, state)
        if (!shouldDrawDecoration) {
            return
        }
        val layoutManager = parent.layoutManager ?: return
        repeat(parent.childCount) {
            val childView = parent.getChildAt(it)
            val leftDecorationWidth = layoutManager.getLeftDecorationWidth(childView)
            val topDecorationHeight = layoutManager.getTopDecorationHeight(childView)
            val rightDecorationWidth = layoutManager.getRightDecorationWidth(childView)
            val bottomDecorationHeight = layoutManager.getBottomDecorationHeight(childView)

            canvas.drawRect(
                Rect(
                    leftDecorationWidth,
                    childView.bottom,
                    childView.width + leftDecorationWidth,
                    childView.bottom + bottomDecorationHeight
                ), paint
            )
        }
    }
}