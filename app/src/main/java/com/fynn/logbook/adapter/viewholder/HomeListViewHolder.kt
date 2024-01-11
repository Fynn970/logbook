package com.fynn.logbook.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.fynn.logbook.bean.ExperimentInfo
import com.fynn.logbook.databinding.ItemHomeBinding

class HomeListViewHolder(private val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {


    fun bind(info: ExperimentInfo){
        binding.tvName.text = info.mName
    }
}