package com.example.cb.test.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.cb.xlibrary.utils.log.XLog;
import com.cb.xlibrary.utils.log.XLogUtils;

/**
 * 描述：
 * 作者：曹斌
 * date:2018/6/6 11:34
 */
public class XService extends IntentService {

    @Override
    public void onCreate() {
        super.onCreate();
        XLog.d("onCreate执行了");
        XLogUtils.d("111111111");
    }

    public XService() {
        super("XService");
        XLog.d("XService构造方法执行了");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        XLog.d("onHandleIntent方法执行了");
        String taskName = intent.getExtras().getString("taskName");
        switch (taskName) {
            case "task1":
                XLog.d("myIntentService  do task1");
                break;
            case "task2":
                XLog.d("myIntentService  do task2");
                break;
            default:
                break;
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        XLog.d("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        XLog.d("onDestroy");
        super.onDestroy();
    }
}
