package com.fynn.logbook.base

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding>(
    val inflater: (LayoutInflater) -> VB
): AppCompatActivity() {

    private var _binding: VB? = null
    protected val binding: VB
        get() = requireNotNull(_binding){
            "The property of binding has been destroyed."
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflater(layoutInflater)
        setContentView(binding.root)

        initData()
        initEvent()
    }

    abstract fun initData()
    abstract fun initEvent()
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun showToast(str: String){
        if(TextUtils.isEmpty(str)){

        }
    }


}