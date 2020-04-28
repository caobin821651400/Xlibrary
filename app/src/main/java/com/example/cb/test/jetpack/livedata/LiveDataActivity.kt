package com.example.cb.test.jetpack.livedata

import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import kotlinx.android.synthetic.main.activity_live_data.*

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/4/22 12:50
 * @Desc :
 * ====================================================
 */
class LiveDataActivity : BaseActivity() {


    override fun getLayoutId(): Int {
        return R.layout.activity_live_data
    }

    override fun initUI() {

    }

    override fun initEvent() {
        btn.setOnClickListener {
            launchActivity(LiveDataActivity2::class.java, null)
            LiveDataBus.getInstance().with("caobin", String::class.java).setValue("哈哈哈哈")
        }

        btn2.setOnClickListener {
            launchActivity(LiveDataActivity2::class.java, null)
            Thread(Runnable {
                for (j in 0..9) {
                    LiveDataBusX.getInstance().with("caobin", String::class.java).postValue("哈哈哈哈")
                    try {
                        Thread.sleep(3000)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }).start()
        }
    }
}