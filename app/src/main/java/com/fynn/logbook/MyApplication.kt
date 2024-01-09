package com.fynn.logbook

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application() {
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var mContext: Context
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: MyApplication
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        mContext = applicationContext
    }


}