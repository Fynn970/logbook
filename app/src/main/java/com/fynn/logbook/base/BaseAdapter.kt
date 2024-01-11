package com.fynn.logbook.base

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BaseAdapter<T>(mContext: Context, mData:MutableList<T>, layoutId: Int): RecyclerView.Adapter<BaseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}