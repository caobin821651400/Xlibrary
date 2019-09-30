package com.example.cb.test.ui.aidl

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.example.cb.test.R
import com.example.cb.test.aidl.IMyService
import com.example.cb.test.aidl.Student
import com.example.cb.test.base.BaseActivity
import com.example.cb.test.service.IStudentService
import kotlinx.android.synthetic.main.activity_aidl_test.*

class AidlTestActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_aidl_test
    }


    override fun initEvent() {
    }

    private var mIMyService: IMyService? = null


    override fun initUI() {
        start.setOnClickListener {
            val intentService = Intent(this, IStudentService::class.java)
            bindService(intentService, mServiceConnection, Context.BIND_AUTO_CREATE)
        }
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
        unbindService(mServiceConnection)
    }
}
