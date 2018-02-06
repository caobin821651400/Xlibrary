package com.cb.xlibrary;


import android.content.Context;

import com.cb.xlibrary.utils.log.XLog;
import com.cb.xlibrary.utils.log.XLogConfig;


public class XLibrary {
    private static Context context;

    // #log
    public static String logTag = "";
    public static boolean isDebug =false;


    public static void init(Context context) {
        XLibrary.context = context;
    }

    public static XLogConfig initXLog() {
        return XLog.init();
    }

    public static Context getContext() {
        synchronized (XLibrary.class) {
            if (XLibrary.context == null)
                throw new NullPointerException("Call XLibrary.init(context) within your Application onCreate() method." +
                        "Or extends XApplication");
            return XLibrary.context.getApplicationContext();
        }
    }
}
