package com.zzq.core.tool.util

import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

/*************************************************************************************************/

@ColorInt
fun RecyclerView.ViewHolder.getColor(@ColorRes id: Int): Int {
    return ContextCompat.getColor(itemView.context, id)
}

fun RecyclerView.ViewHolder.getDrawable(@DrawableRes id: Int): Drawable? {
    return ContextCompat.getDrawable(itemView.context, id)
}

fun RecyclerView.ViewHolder.getString(@StringRes id: Int): String {
    return itemView.context.getString(id)
}

/*************************************************************************************************/

