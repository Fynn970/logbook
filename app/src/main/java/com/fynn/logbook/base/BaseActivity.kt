package com.fynn.logbook.base

import android.os.Bundle
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
        _binding = inflater(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}