package com.example.popupsdksample

import android.app.Application
import com.example.popupsdksample.SmartComSDK

class SDKApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SmartComSDK.init(this)
    }
}