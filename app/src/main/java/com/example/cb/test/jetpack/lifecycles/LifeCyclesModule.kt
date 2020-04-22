package com.example.cb.test.jetpack.lifecycles

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import cb.xlibrary.utils.XLogUtils
import com.example.cb.test.base.BaseActivity

class LifeCyclesModule:LifecycleObserver {




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