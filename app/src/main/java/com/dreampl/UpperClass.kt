package com.dreampl

import android.app.Application
import android.content.Context
import com.dreampl.blk.STN.MAIN_ID
import com.dreampl.blk.STN.ONESIGNAL_APP_ID
import com.dreampl.blk.Vert
import com.onesignal.OneSignal
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@HiltAndroidApp
class UpperClass: Application() {
    override fun onCreate() {
        super.onCreate()
        Hawk.init(this).build()
        GlobalScope.launch(Dispatchers.IO) {
            applyDeviceId(context = applicationContext)
        }
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
    }

    private suspend fun applyDeviceId(context: Context) {
        val advertisingInfo = Vert(context)
        val idInfo = advertisingInfo.getAdvertisingId()
        Hawk.put(MAIN_ID, idInfo)
    }
}