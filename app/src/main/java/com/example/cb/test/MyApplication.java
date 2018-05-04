package com.example.cb.test;

import android.app.Application;

import com.cb.xlibrary.XLibrary;
import com.cb.xlibrary.utils.XCrashHandlerUtils;

/**
 * Created by cb on 2017/12/1.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

//        XCrashHandlerUtils.getInstance().init(this);
        XLibrary.init(this);
        XLibrary.logTag = "自定义日志";
        XLibrary.isDebug = true;
    }
}
