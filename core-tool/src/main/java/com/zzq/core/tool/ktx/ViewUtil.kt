package com.zzq.core.tool.ktx

import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zzq.core.tool.R
import com.zzq.core.tool.ui.LinearItemDecoration

fun Fragment.setRecyclerView(
    view: View,
    @IdRes resId: Int,
    adapter: RecyclerView.Adapter<*>,
    layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext()),
    itemDecoration: RecyclerView.ItemDecoration = LinearItemDecoration(lineColor = R.color.text_second_white)
): RecyclerView {
    //这里不能用getView()
    val recyclerView = view.findViewById<RecyclerView>(resId)
    recyclerView.layoutManager = layoutManager
    recyclerView.addItemDecoration(itemDecoration)
    recyclerView.adapter = adapter
    return recyclerView
}

fun Fragment.setRecyclerView(
    rv:RecyclerView,
    adapter: RecyclerView.Adapter<*>,
    layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext()),
    itemDecoration: RecyclerView.ItemDecoration = LinearItemDecoration(lineColor = R.color.text_second_white)
): RecyclerView {
    rv.layoutManager = layoutManager
    rv.addItemDecoration(itemDecoration)
    rv.adapter = adapter
    return rv
}