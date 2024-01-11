package com.fynn.logbook.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

//    public <T extends View> T getView(int viewId) {    View view = mViews.get(viewId);    if (view == null) {        view = mItemView.findViewById(viewId);        mViews.put(viewId, view);    }    return (T) view;}
//
//    fun setOnLongClickListener(viewId: Int, listener: View.OnLongClickListener): BaseViewHolder {
//        val view = getView (viewId); view.setOnLongClickListener(listener); return this; }

}