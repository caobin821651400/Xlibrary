package com.cb.xlibrary;


import android.content.Context;


public class XLibrary {
    private static Context context;

    // #log
    public static String logTag = "TT";
    public static boolean isDebug = false;


    public static void init(Context context) {
        XLibrary.context = context;
    }

    public static Context getContext() {
        synchronized (XLibrary.class) {
            if (XLibrary.context == null)
                throw new NullPointerException("XLibrary未初始化");
            return XLibrary.context.getApplicationContext();
        }
    }
}
