package com.example.cb.test;

import android.app.Application;

import cb.xlibrary.XLibrary;
import dagger.hilt.android.HiltAndroidApp;


/**
 * Created by cb on 2017/12/1.
 */
@HiltAndroidApp
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        XCrashHandlerUtils.getInstance().init(this);
        XLibrary.init(this);
        XLibrary.logTag = "ME日志";
        XLibrary.isDebug = true;

//        CrashReport.initCrashReport(getApplicationContext(), "c4163937f8", true);
    }
}
