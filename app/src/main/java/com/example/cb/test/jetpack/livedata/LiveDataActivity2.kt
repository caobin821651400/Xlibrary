package com.example.cb.test.jetpack.livedata

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import cb.xlibrary.utils.XLogUtils
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import kotlinx.android.synthetic.main.activity_live_data2.*

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/4/22 12:50
 * @Desc :
 * ====================================================
 */
class LiveDataActivity2 : BaseActivity() {

    var liveDataModule: NameViewModule? = null
    var i = 0


    override fun getLayoutId(): Int {
        return R.layout.activity_live_data2
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


        //第1种传值方法,绑定后每次进来都会执行一次
        LiveDataBus.getInstance().with("caobin", String::class.java).observe(this, Observer {
            XLogUtils.d("liveDta-->" + "第1种 " + it)
            text.text = "第1种 " + it
            toast("第1种 " + it)
        })

        //第二种传值方法
        LiveDataBusX.getInstance().with("caobin", String::class.java).observe(this, Observer<String> {
            XLogUtils.d("liveDta-->" + "第2种 " + it)
            text.text = "第2种 " + it
            toast("第2种 " + it)
        })
    }

    override fun initEvent() {
        btn.setOnClickListener {
            var ss = "点击 + ${i++}"
            liveDataModule!!.currentName!!.value = ss
        }
    }
}