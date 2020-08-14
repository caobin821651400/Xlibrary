package com.example.cb.test.jetpack.livedata

import androidx.lifecycle.Observer
import cn.sccl.xlibrary.utils.XLogUtils
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import kotlinx.android.synthetic.main.activity_live_data.*
import org.json.JSONObject

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
        setHeaderTitle("LiveData使用")
        LiveDataBus2.getInstance().with("caobin_back", String::class.java).observe(this,
                Observer<String> {
                    //
                    XLogUtils.e("接收回传-->$it");
                })
    }

    override fun initEvent() {
        btn.setOnClickListener {
            launchActivity(LiveDataActivity2::class.java, null)
            LiveDataBus2.getInstance().with("caobin", String::class.java).setValue("哈哈哈哈")
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

        btn3.setOnClickListener {
            launchActivity(LiveDataActivity2::class.java, null)

//            btn3.postDelayed({

                val jsonObject = JSONObject()
                jsonObject.put("name", "1111")
                LiveDataBusEvent.getFromData().postValue(jsonObject)
//            },2000)
        }
    }
}
