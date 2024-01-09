package com.fynn.logbook.util

import android.content.Context
import androidx.annotation.StringRes
import com.fynn.logbook.MyApplication


fun getApplicationContext():Context{
    return MyApplication.mContext
}

fun showToast(string: String){
    ToastUtil.showShort(getApplicationContext(), string)
}

fun showToast(@StringRes resStr: Int){
    ToastUtil.showShort(getApplicationContext(), resStr)
}