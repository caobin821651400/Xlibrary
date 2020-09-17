package com.example.cb.test.ui.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.lifecycle.MutableLiveData
import com.example.cb.test.service.MyService
import kotlinx.coroutines.*


class BackgroundService : Service() {

    val mViewModel = MutableLiveData<String>()

    private val mBinder = MBinder()

    override fun onBind(intent: Intent?): IBinder? {
        return mBinder
    }

    override fun onCreate() {
        super.onCreate()
        start()
    }

    private suspend fun aaa() {
        withContext(Dispatchers.IO) {
            delay(5000)
            withContext(Dispatchers.Main) {
                mViewModel.postValue("11")
            }
        }
    }

    public fun start() {
        CoroutineScope(Dispatchers.Main).launch {
            aaa()
        }
    }

    inner class MBinder : Binder() {

        fun getService(): BackgroundService {
            return this@BackgroundService
        }
    }
}