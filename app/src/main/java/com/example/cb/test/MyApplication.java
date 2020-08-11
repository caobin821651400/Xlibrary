package com.example.cb.test;

import android.app.Application;

import androidx.multidex.MultiDexApplication;

import com.tencent.bugly.crashreport.CrashReport;

import cb.xlibrary.XLibrary;
import cb.xlibrary.utils.XCrashHandlerUtils;
import dagger.hilt.android.HiltAndroidApp;


/**
 * Created by cb on 2017/12/1.
 */
@HiltAndroidApp
public class MyApplication extends MultiDexApplication {

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
