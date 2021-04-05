package com.example.cb.test.jetpack.paging

import android.os.Handler
import android.os.Looper
import cn.sccl.http.XHttp
import cn.sccl.xlibrary.utils.XLogUtils
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import com.example.cb.test.kotlin.coroutines.net.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.HashMap

class PagingActivity : BaseActivity(), CoroutineScope by MainScope() {

    override fun initUI() {

       Handler(Looper.myLooper()!!).postDelayed({getData()},2000)

      val  map =HashMap<String,String>()
        map.put("1","1")
    }


    private fun getData() {
        launch {
            val response = XHttp.getService(ApiService::class.java).getGitHubData(1, 10)

            XLogUtils.d("${response.items[0].description}")
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_paging
    }

    override fun initEvent() {
    }
}
