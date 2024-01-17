package com.fynn.logbook.base

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T, VB:ViewBinding>(
    private val mContext: Context,
    private val mData:MutableList<T>,
    val inflater: (LayoutInflater,ViewGroup,Boolean, ) -> VB): RecyclerView.Adapter<BaseViewHolder<VB>>() {

    private lateinit var mLongItemClickListener: onLongItemClickListener
    private lateinit var mItemClickListener: OnItemClickListener

    fun updateData(data: List<T>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    fun addAll(data: List<T>) {
        mData.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
        return BaseViewHolder(inflater.invoke(LayoutInflater.from(mContext), parent, false))
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<VB>, position: Int) {
        convert(mContext, holder, mData[position], position)
        holder.itemView.setOnClickListener { v ->
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, position)
            }
        }
        if (mLongItemClickListener != null) {
            holder.itemView.setOnLongClickListener { v ->
                mLongItemClickListener.onLongItemClick(v, position)
                true
            }
        }
    }

     abstract fun convert(
        mContext: Context,
        holder: BaseViewHolder<VB>,
        t: T,
        select_position: Int
    )

    open fun getData(): List<T>? {
        return mData
    }

    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

    interface onLongItemClickListener {
        fun onLongItemClick(view: View?, postion: Int)
    }

    open fun setOnItemClickListener(listener: OnItemClickListener) {
        this.mItemClickListener = listener
    }

    open fun setonLongItemClickListener(listener: onLongItemClickListener) {
        this.mLongItemClickListener = listener
    }

}