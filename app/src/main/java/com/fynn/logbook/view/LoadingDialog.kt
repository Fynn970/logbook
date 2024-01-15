package com.fynn.logbook.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import com.fynn.logbook.R

class LoadingDialog {
    private var showCount = 0
    private var dialog : AlertDialog? = null

    fun show(activity: FragmentActivity?){
        activity ?: return

        if(++showCount > 1){
            return
        }

        dialog = AlertDialog.Builder(activity)
            .setView(R.layout.dialog_loading)
            .create()

        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.show()
        showCount = 1
        dialog?.window?.let { window ->
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val params = window.attributes
            params.gravity = Gravity.CENTER
            window.attributes = params
        }
    }

    fun dismiss(){
        if(--showCount <= 0){
            dialog?.dismiss()
        }
    }

    fun notShow(): Boolean {
        return showCount <= 0 && dialog?.isShowing != true
    }
}