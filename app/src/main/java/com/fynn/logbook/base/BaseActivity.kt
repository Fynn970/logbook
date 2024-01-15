package com.fynn.logbook.base

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.viewbinding.ViewBinding
import com.fynn.logbook.databinding.BaseViewBinding
import com.fynn.logbook.view.LoadingDialog

abstract class BaseActivity<VB : ViewBinding>(
    val inflater: (LayoutInflater) -> VB
): AppCompatActivity() {
    private lateinit var baseBinding: BaseViewBinding
    private lateinit var dialog: LoadingDialog
    private var _binding: VB? = null
    protected val binding: VB
        get() = requireNotNull(_binding){
            "The property of binding has been destroyed."
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseBinding = BaseViewBinding.inflate(layoutInflater)
        _binding = inflater(layoutInflater)
        baseBinding.baseContainer.addView(_binding?.root)
        setContentView(baseBinding.root)

        baseBinding.rlBack.setOnClickListener {
            backClickListener()
        }

        dialog = LoadingDialog()
        initView()
        initData()
        initEvent()
    }

    open fun initView(){

    }
    abstract fun initData()
    abstract fun initEvent()
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun showBack(isShow: Boolean){
        if (isShow){
            baseBinding.rlBack.visibility = View.VISIBLE
        }else{
            baseBinding.rlBack.visibility = View.INVISIBLE
        }
    }

    fun showLoading(){
        if (dialog.notShow()) {
            dialog.show(this)
        }
    }

    fun hideLoading(){
        if (!dialog.notShow()){
            dialog.dismiss()
        }
    }

    open fun backClickListener(){
        finish()
    }

    fun <T> intentView(classa: Class<T>){
        startActivity(Intent(this, classa))
    }

    fun setOperate(@DrawableRes drawable: Int, clickListener: ()->Unit){
        baseBinding.rlOperate.visibility = View.VISIBLE
        baseBinding.ivOpera.setImageDrawable(ResourcesCompat.getDrawable(resources, drawable, null))
        baseBinding.rlOperate.setOnClickListener {
            clickListener.invoke()
        }
    }
}