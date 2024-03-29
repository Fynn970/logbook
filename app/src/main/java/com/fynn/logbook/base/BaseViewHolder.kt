package com.fynn.logbook.base

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.SpannableStringBuilder
import android.util.SparseArray
import android.view.View
import android.view.View.OnLongClickListener
import android.view.View.OnTouchListener
import android.view.animation.AlphaAnimation
import android.widget.Checkable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class BaseViewHolder<VB:ViewBinding>(
    val vb:VB) : RecyclerView.ViewHolder(vb.root) {
    private var mViews: SparseArray<View> = SparseArray()
    private val mItemView:View
    init {
        mItemView = itemView
    }



//    fun <T:View> getView(viewId: Int):T{
//        var view = mViews.get(viewId)
//        if (view == null){
//            view = mItemView.findViewById(viewId)
//            mViews.put(viewId, view)
//        }
//        return view as T
//    }
//
//    fun setText(viewId: Int, resId: Int): BaseViewHolder {
//        val textView = getView<TextView>(viewId)
//        textView.setText(resId)
//        return this
//    }
//
//
//    fun setText(viewId: Int, text: String?): BaseViewHolder {
//        val textView = getView<TextView>(viewId)
//        textView.text = text
//        return this
//    }
//
//    fun setText(viewId: Int, text: SpannableStringBuilder?): BaseViewHolder {
//        val textView = getView<TextView>(viewId)
//        textView.text = text
//        return this
//    }
//
//    fun setImageResource(viewId: Int, resId: Int): BaseViewHolder {
//        val view = getView<ImageView>(viewId)
//        view.setImageResource(resId)
//        return this
//    }
//
////    fun setImageResource(viewId: Int, url: String?): BaseViewHolder? {
////        val view = getView<ImageView>(viewId)
////        Glide.with(itemView.context).load(url).dontAnimate().into(view)
////        return this
////    }
////
////    fun setRadioImageResource(viewId: Int, resId: Int): BaseViewHolder? {
////        val view: RadiuImageView = getView(viewId)
////        view.setImageResource(resId)
////        return this
////    }
//
//    fun setImageBitmap(viewId: Int, bitmap: Bitmap?): BaseViewHolder {
//        val view = getView<ImageView>(viewId)
//        view.setImageBitmap(bitmap)
//        return this
//    }
//
//    fun setImageDrawable(viewId: Int, drawable: Drawable?): BaseViewHolder {
//        val view = getView<ImageView>(viewId)
//        view.setImageDrawable(drawable)
//        return this
//    }
//
//    fun setBackgroundColor(viewId: Int, color: Int): BaseViewHolder {
//        val view = getView<View>(viewId)
//        view.setBackgroundColor(color)
//        return this
//    }
//
//    fun setBackgroundResource(viewId: Int, backgroundRes: Int): BaseViewHolder {
//        val view = getView<View>(viewId)
//        view.setBackgroundResource(backgroundRes)
//        return this
//    }
//
//
//    fun setTextColor(viewId: Int, textColor: Int): BaseViewHolder {
//        val view = getView<TextView>(viewId)
//        view.setTextColor(textColor)
//        return this
//    }
//
//    fun getVisiable(view: Int): Int {
//        return getView<View>(view).visibility
//    }
//
//
//    @SuppressLint("NewApi")
//    fun setAlpha(viewId: Int, value: Float): BaseViewHolder {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            getView<View>(viewId).alpha = value
//        } else {
//            // Pre-honeycomb hack to set Alpha value
//            val alpha = AlphaAnimation(value, value)
//            alpha.duration = 0
//            alpha.fillAfter = true
//            getView<View>(viewId).startAnimation(alpha)
//        }
//        return this
//    }
//
//    fun setVisible(viewId: Int, visible: Int): BaseViewHolder {
//        val view = getView<View>(viewId)
//        view.visibility = visible
//        return this
//    }
//
//
//    fun setTag(viewId: Int, tag: Any?): BaseViewHolder {
//        val view = getView<View>(viewId)
//        view.tag = tag
//        return this
//    }
//
//    fun setTag(viewId: Int, key: Int, tag: Any?): BaseViewHolder {
//        val view = getView<View>(viewId)
//        view.setTag(key, tag)
//        return this
//    }
//
//    fun setChecked(viewId: Int, checked: Boolean): BaseViewHolder {
//        val view = getView<View>(viewId) as Checkable
//        view.isChecked = checked
//        return this
//    }

//    /**
//     * 关于事件监听
//     */
//    fun setOnClickListener(viewId: Int, listener: View.OnClickListener?): BaseViewHolder {
//        val view = getView<View>(viewId)
//        view.setOnClickListener(listener)
//        return this
//    }
//
//    fun setOnTouchListener(viewId: Int, listener: OnTouchListener?): BaseViewHolder {
//        val view = getView<View>(viewId)
//        view.setOnTouchListener(listener)
//        return this
//    }
//
//    fun setOnLongClickListener(viewId: Int, listener: OnLongClickListener?): BaseViewHolder {
//        val view = getView<View>(viewId)
//        view.setOnLongClickListener(listener)
//        return this
//    }
}