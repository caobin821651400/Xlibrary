package com.example.cb.test.jetpack.livedata

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import cb.xlibrary.utils.XLogUtils
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

    var liveDataModule: NameViewModule? = null
    var i = 0


    override fun getLayoutId(): Int {
        return R.layout.activity_live_data
    }

    override fun initUI() {
        liveDataModule = ViewModelProviders.of(this).get(NameViewModule::class.java)

        //需要一个观察者来观察变换
        val observer = object : Observer<String> {
            override fun onChanged(t: String) {
                text.text = t
            }
        }

        //订阅
        liveDataModule!!.currentName!!.observe(this, observer)

        //第二种传值方法
        LiveDataBus.getInstance().with("caobin", String::class.java).observe(this, Observer {
            XLogUtils.d("liveDta-->" + it)
            text.text = it
            toast("斤斤计较 " + it)
        })
    }

    override fun initEvent() {
        btn.setOnClickListener {
            var ss = "点击 + ${i++}"
            liveDataModule!!.currentName!!.value = ss
        }
    }
}
