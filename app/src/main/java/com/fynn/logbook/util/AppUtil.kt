package com.fynn.logbook.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat.getSystemService
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

fun hideSoftKey(context: Context, view: View){
    val manager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    manager.hideSoftInputFromWindow(view.windowToken, 0)
}