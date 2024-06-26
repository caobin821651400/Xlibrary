package com.example.cb.test.ui.aidl

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import android.widget.Button
import cn.sccl.xlibrary.kotlin.lazyNone
import com.example.cb.test.R
import com.example.cb.test.aidl.IMyService
import com.example.cb.test.aidl.Student
import com.example.cb.test.base.BaseActivity
import com.example.cb.test.service.IStudentService
import okhttp3.*
import java.io.IOException

class AidlTestActivity : BaseActivity() {
    private val btnGet by lazyNone { findViewById<Button>(R.id.btnGet) }
    private val start by lazyNone { findViewById<Button>(R.id.start) }
    override fun getLayoutId(): Int {
        return R.layout.activity_aidl_test
    }


    override fun initEvent() {
        btnGet.setOnClickListener {
            var student: Student = mIMyService!!.student[0]
            Log.e("btnGet-> ", student.name)
        }
    }

    private var mIMyService: IMyService? = null


    override fun initUI() {
        setHeaderTitle("AIDL")
        start.setOnClickListener {
            val intentService = Intent(this, IStudentService::class.java)
            bindService(intentService, mServiceConnection, Context.BIND_AUTO_CREATE)
        }

        val client = OkHttpClient.Builder().build()
        val result = Request.Builder().build()


        val call = client.newCall(result).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
            }
        })
    }

    /**
     * service连接监听
     */
    private var mServiceConnection = object : ServiceConnection {
        /**
         * 服务连接成功
         */
        override fun onServiceConnected(name: ComponentName?, iBinder: IBinder?) {
            //通过服务端onBind方法返回的binder对象得到IMyService的实例，得到实例就可以调用它的方法了
            mIMyService = IMyService.Stub.asInterface(iBinder)
            var student: Student = mIMyService!!.student[0]
            Log.d("aaa ", student.name)
        }

        /**
         * 服务连接失败
         */
        override fun onServiceDisconnected(name: ComponentName?) {
            mIMyService = null
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            mServiceConnection?.let { unbindService(mServiceConnection) }
            stopService(Intent(this, IStudentService::class.java))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
