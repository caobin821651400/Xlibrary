package com.example.cb.test.jetpack.lifecycles

import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/4/21 12:28
 * @Desc : 生命周期
 * ====================================================
 */
class LifeCyclesActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_life_cycles
    }

    override fun initUI() {
        setHeaderTitle("LifeCycles使用")

        val bb = LifeCyclesModule2()


        lifecycle.addObserver(bb)
    }

    override fun onStart() {
        val aa = LifeCyclesModule()
        lifecycle.addObserver(aa)
        super.onStart()
    }

    override fun initEvent() {
    }

//
//    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
//    fun onMStop() {
//        XLogUtils.d("Lifecycle.Event.ON_STOP")
//    }
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
//    fun onMStop2() {
//        XLogUtils.d("Lifecycle.Event.ON_STOP2")
//    }
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
//    fun onmResume() {
//        XLogUtils.d("Lifecycle.Event.ON_RESUME")
//    }
}
