package com.zzq.core.tool.base

import androidx.recyclerview.widget.RecyclerView
import com.zzq.core.tool.interfaces.OnItemClickListener

/**
 * T指显示在列表中的实体类
 * K是ViewHolder类型，可直接使用BaseViewHolder
 */
abstract class BaseAdapter<T, K : BaseViewHolder> : RecyclerView.Adapter<K>() {
    var mOnItemClickListener: OnItemClickListener<T>? = null

    protected val TYPE_EMPTY = 89
    protected val TYPE_DATA = 95

    val dataList: ArrayList<T> = ArrayList<T>()

    /**
     * 获取数据源
     */
    fun getDataSource(): ArrayList<T> {
        return dataList
    }

    /**
     * 更新数据源。位置不变的前提下使用
     */
    fun updateDataSource(newData: ArrayList<T>) {

        if (dataList.size == 0) {
            dataList.addAll(newData)
            notifyItemRangeInserted(0, newData.size - 1)
        } else {
            val size = dataList.size
            notifyItemRangeChanged(0, dataList.size)
            repeat(newData.size - size) {
                dataList.add(newData[it + size])
            }
            notifyItemRangeInserted(size, newData.size - size)
        }
//        notifyDataSetChanged()
    }

    fun clearData() {
        val size = dataList.size
        if (size == 0) {
            return
        }
        dataList.clear()
        notifyItemRangeRemoved(0, size)
    }

    override fun getItemCount(): Int {
        return if (dataList.size == 0) {
            1
        } else {
            dataList.size
        }
    }

    override fun getItemViewType(position: Int): Int {

        return if (dataList.size == 0) {
            TYPE_EMPTY
        } else {
            TYPE_DATA
        }
    }

    override fun onBindViewHolder(holder: K, position: Int) {
        if (dataList.size == 0) {
            return
        }
        val adapterPosition: Int = holder.adapterPosition
        convert(adapterPosition, dataList[adapterPosition], holder)
    }

    /**
     * 移除设备
     */
    fun removeItem(position: Int) {
        if (position < 0 && dataList.size < 1) {
            return
        }
        dataList.removeAt(position)
        notifyItemRemoved(position)
    }

    abstract fun convert(position: Int, itemData: T, holder: K)
}