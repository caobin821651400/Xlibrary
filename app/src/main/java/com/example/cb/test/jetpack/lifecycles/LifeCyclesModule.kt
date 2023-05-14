package com.example.cb.test.jetpack.lifecycles

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import cn.sccl.xlibrary.utils.XLogUtils

class LifeCyclesModule:LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onMCreate() {
        XLogUtils.d("onMCreate 1111")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onMStart() {
        XLogUtils.d("Lifecycle.Event.ON_START 1111")
    }



    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onMStop() {
        XLogUtils.d("Lifecycle.Event.ON_STOP")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onMStop2() {
        XLogUtils.d("Lifecycle.Event.ON_STOP2")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onmResume() {
        XLogUtils.d("Lifecycle.Event.ON_RESUME")
    }

}