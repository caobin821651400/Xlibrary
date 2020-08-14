package com.example.cb.test.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import androidx.annotation.Nullable;

import cn.sccl.xlibrary.utils.XLogUtils;


/**
 * 描述：重复创建只会调用一次onCreate()和构造方法。
 * IntentService在onCreate()函数中通过HandlerThread单独开启一个线程来依次处理所有Intent请求对象所对应的任务。
 * 通过onStartCommand()传递给服务intent被依次插入到工作队列中。工作队列又把intent逐个发送给onHandleIntent()。
 * 注意： 它只有一个工作线程，名字就是构造函数的那个字符串，也就是“XService”，我们知道多次开启service，
 * 只会调用一次onCreate方法（创建一个工作线程），多次onStartCommand方法（用于传入intent通过工作队列再发给onHandleIntent函数做处理）。
 * 作者：曹斌
 * date:2018/6/6 11:34
 */
public class XService extends IntentService {

    @Override
    public void onCreate() {
        super.onCreate();
        XLogUtils.d("onCreate执行了");
    }

    public XService() {
        super("XService");
        XLogUtils.d("XService构造方法执行了");
    }

    public void aa() {
        XLogUtils.d("1111111111");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //在这里执行耗时操作
        XLogUtils.d("onHandleIntent方法执行了");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String taskName = intent.getStringExtra("taskName");
        switch (taskName) {
            case "task1":
                XLogUtils.d("myIntentService  do task1");
                break;
            case "task2":
                XLogUtils.d("myIntentService  do task2");
                break;
            default:
                break;
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        XLogUtils.d("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public class MyBinder extends Binder {
        public XService getService() {
            return XService.this;
        }
    }

    @Override
    public void onDestroy() {
        XLogUtils.d("onDestroy");
        super.onDestroy();
    }
}
