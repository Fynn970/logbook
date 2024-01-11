package com.fynn.logbook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fynn.logbook.adapter.viewholder.HomeListViewHolder
import com.fynn.logbook.bean.ExperimentInfo
import com.fynn.logbook.databinding.ItemHomeBinding

class HomeListAdapter(private val mContext: Context, private val mData:MutableList<ExperimentInfo> = mutableListOf()): RecyclerView.Adapter<HomeListViewHolder>() {

    fun setData(data: List<ExperimentInfo>){
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeListViewHolder {
        return HomeListViewHolder(ItemHomeBinding.inflate(LayoutInflater.from(mContext), parent, false))
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: HomeListViewHolder, position: Int) {
        holder.bind(mData[position])
    }
}