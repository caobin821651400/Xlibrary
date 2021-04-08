package com.example.cb.test.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import cn.sccl.xlibrary.utils.XLogUtils

class TestService : Service() {


    override fun onBind(intent: Intent?): IBinder? {
        XLogUtils.d("onBind")
        return null
    }

    override fun onUnbind(intent: Intent?): Boolean {
        XLogUtils.d("onUnbind")
        return super.onUnbind(intent)
    }

    override fun onCreate() {
        super.onCreate()
        XLogUtils.d("onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        XLogUtils.d("onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }


    override fun onDestroy() {
        super.onDestroy()
        XLogUtils.d("onDestroy")
    }
}