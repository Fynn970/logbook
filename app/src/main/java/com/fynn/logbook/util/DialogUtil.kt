package com.fynn.logbook.util

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.fynn.logbook.R
import com.fynn.logbook.databinding.DialogCustomBinding
import java.util.Calendar


fun twoBtnDialog(
    context: Context,
    msg: String = "",
    positionText:String = context.getString(R.string.sure),
    negativeText:String = context.getString(R.string.cancel),
    cancel: () -> Unit={},
    sure: Dialog.() -> Unit = {}
) {
    val binding = DialogCustomBinding.inflate(LayoutInflater.from(context))
    val dialog = Dialog(context)
    dialog.setCanceledOnTouchOutside(false)
    dialog.setCancelable(false)
    dialog.setContentView(binding.root)
    binding.tvSure.text = positionText
    binding.tvCancel.text = negativeText
    binding.tvMessage.text = msg
    binding.tvSure.setOnClickListener {
        sure.invoke(dialog)
        dialog.dismiss()
    }
    binding.tvCancel.setOnClickListener {
        cancel.invoke()
        dialog.dismiss()
    }
    dialog.show()
}

fun showDatePickerDialog(context: Context,listener: String.() -> Unit) {
    val calendar = Calendar.getInstance()
    calendar[Calendar.YEAR] = 1990 // 设置年份
    calendar[Calendar.MONTH] = Calendar.JANUARY // 设置月份（注意：月份从 0 开始，January 为 0）
    calendar[Calendar.DAY_OF_MONTH] = 1 // 设置日期
    val dialog = DatePickerDialog(
        context,
        { _, year, monthOfYear, dayOfMonth ->
            listener.invoke(
                dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/"
                        + year
            )
        }, calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH]
    )
    dialog.show()
}


//fun sliderDialog(
//    context: Context,
//    background: ByteArray,
//    cutImg: ByteArray,
//    width: Int,
//    height:Int,
//    x:Int,
//    y:Int,
//    precision: Int,
//    sure: (Dialog, Int, Int) -> Unit
//) {
//    val binding = DialogSliderBinding.inflate(LayoutInflater.from(context))
//    val dialog = Dialog(context)
//    dialog.setCanceledOnTouchOutside(false)
//    dialog.setCancelable(false)
//    dialog.setContentView(binding.root)
//    binding.sivSlideview.setBackground(background)
//        .setSlide(cutImg,width, height, x, y)
//        .setPrecision(precision)
//        .setListener(object : SliderView.OnSlideListener{
//            override fun onSlideListener(v: Int, top: Int) {
//                sure(dialog, v, top)
//                dialog.dismiss()
//            }
//        })
//    dialog.show()
//}
//
//fun <T,VB: ViewBinding> verSelectorDialog(
//    context: Context,
//    list:MutableList<T> = mutableListOf(),
//    inflate: (LayoutInflater, ViewGroup, Boolean) -> VB,
//    viewHolder: (CustomViewHolder<VB>, T, Int)->Unit,
//    callback: (T, Int)->Unit = {_,_-> }
//){
//    val binding = DialogSelectorBinding.inflate(LayoutInflater.from(context))
//    val dialog = Dialog(context)
//    dialog.setCanceledOnTouchOutside(true)
//    dialog.setCancelable(true)
//    dialog.setContentView(binding.root)
//    binding.rvView.let {
//        it.layoutManager = LinearLayoutManager(context)
//        val customAdapter = object :CustomAdapter<T, VB>(
//            context,
//            list,
//            inflate
//        ){
//            override fun convert(
//                mContext: Context,
//                holder: CustomViewHolder<VB>,
//                t: T,
//                select_position: Int
//            ) {
//                viewHolder.invoke(holder, t, select_position)
//                holder.itemView.setOnClickListener { v->
//                    callback.invoke(t, select_position)
//                    dialog.dismiss()
//                }
//            }
//
//        }
//        it.adapter = customAdapter
//    }
//    dialog.show()
//
//    val window: Window? = dialog.getWindow()
//    window?.let {
//        val lp = it.attributes
//        lp.gravity = Gravity.CENTER
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT //宽高可设置具体大小
//
//        // lp.height = WindowManager.LayoutParams.MATCH_PARENT;
//        // lp.width  = 400;
//        // lp.height = WindowManager.LayoutParams.MATCH_PARENT;
//        // lp.width  = 400;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
//        it.attributes = lp
//    }
//}
//
//fun repayDetailsDialog(context: Context, repayInfoModel: RepayInfoModel){
//    val binding = DialogRepayDetailsBinding.inflate(LayoutInflater.from(context))
//    val dialog = Dialog(context)
//    dialog.setCanceledOnTouchOutside(true)
//    dialog.setCancelable(true)
//    dialog.setContentView(binding.root)
//    binding.tvTotalAmount.text = "${getStartInfo().kuqM3}${repayInfoModel.Uqqt643}"
//    val mFeelistAdapter = object : CustomAdapter<CSL0567, ItemRepayDetaisdBinding>(
//        context,
//        repayInfoModel.CSL0567.toMutableList(),
//        ItemRepayDetaisdBinding::inflate
//    ) {
//        override fun convert(
//            mContext: Context,
//            holder: CustomViewHolder<ItemRepayDetaisdBinding>,
//            t: CSL0567,
//            select_position: Int
//        ) {
//            holder.getBinding().apply {
//                tvTitle.text = t.fidQ7
//                tvValue.text = t.value
//            }
//        }
//    }
//    binding.rvFeelist.layoutManager = LinearLayoutManager(context)
//    binding.rvFeelist.adapter = mFeelistAdapter
//    dialog.show()
//}
//
//fun <VB:ViewBinding> customDialog(context: Context,
//                 list: List<ContactUsModelItem>,
//                 inflate: (LayoutInflater, ViewGroup, Boolean) -> VB,
//                 viewHolder: (CustomViewHolder<VB>, ContactUsModelItem, Int)->Unit,
//                 callback: (ContactUsModelItem, Int)->Unit = {_,_-> }){
//    val binding = DialogSelectorBinding.inflate(LayoutInflater.from(context))
//    val dialog = Dialog(context)
//    dialog.setContentView(binding.root)
//
//    binding.rvView.let {
//        it.setBackgroundResource(R.color.custom_bg)
//        it.layoutManager = GridLayoutManager(context, 2)
//        val customAdapter = object :CustomAdapter<ContactUsModelItem, VB>(
//            context,
//            list.toMutableList(),
//            inflate
//        ){
//            override fun convert(
//                mContext: Context,
//                holder: CustomViewHolder<VB>,
//                t: ContactUsModelItem,
//                select_position: Int
//            ) {
//                viewHolder.invoke(holder, t, select_position)
//                holder.itemView.setOnClickListener { v->
//                    callback.invoke(t, select_position)
//                    dialog.dismiss()
//                }
//            }
//
//        }
//        it.adapter = customAdapter
//    }
//    dialog.show()
//}
//
//fun updateDialog(context: Context,
//                 updateAppResult: UpdateAppResult,
//                 updateListener: ()->Unit = {}){
//    val binding = DialogUpdateBinding.inflate(LayoutInflater.from(context))
//    val dialog = Dialog(context)
//    dialog.setCanceledOnTouchOutside(false)
//    dialog.setCancelable(false)
//    dialog.setContentView(binding.root)
//    if (updateAppResult.Xnc0.isNullOrEmpty()){
//        binding.tvUpdateNum.text = "${context.getString(R.string.update_num)}"
//    }else{
//        binding.tvUpdateNum.text = "${context.getString(R.string.update_num)}${updateAppResult.Xnc0}"
//    }
//    updateAppResult.pYoa991?.let {
//        binding.tvUpdateDetails.text = it
//    }
//    if (updateAppResult.QnJ31){
//        binding.tvNoUpdate.visibility = View.GONE
//    }
//    binding.tvUpdate.setOnClickListener {
//        updateListener.invoke()
//    }
//
//    binding.tvNoUpdate.setOnClickListener {
//        dialog.dismiss()
//    }
//    dialog.show()
//    val window: Window? = dialog.getWindow()
//    window?.let {
//        val lp = it.attributes
//        lp.gravity = Gravity.CENTER
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT //宽高可设置具体大小
//
//        // lp.height = WindowManager.LayoutParams.MATCH_PARENT;
//        // lp.width  = 400;
//        // lp.height = WindowManager.LayoutParams.MATCH_PARENT;
//        // lp.width  = 400;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
//        it.attributes = lp
//    }
//}
//
//fun homeLoadDialog(context: Context):Dialog{
//    val binding = DialogHomeLoadBinding.inflate(LayoutInflater.from(context))
//    Glide.with(context).asGif().load(R.drawable.bg_home_load).into(binding.ivLoadgif)
//    val dialog = Dialog(context)
//    dialog.setCanceledOnTouchOutside(false)
//    dialog.setCancelable(false)
//    dialog.setContentView(binding.root)
//    dialog.show()
//    return dialog
//}