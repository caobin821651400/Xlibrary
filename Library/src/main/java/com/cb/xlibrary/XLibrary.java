package com.cb.xlibrary;


import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.DisplayMetrics;

import com.cb.xlibrary.utils.XDensityUtils;
import com.cb.xlibrary.utils.XOutdatedUtils;
import com.cb.xlibrary.utils.log.XLog;
import com.cb.xlibrary.utils.log.XLogConfig;


public class XLibrary {
    private static Context context;
    public static int screenHeight;
    public static int screenWidth;

    // #log
    public static String tag = "XLibrary";
    public static boolean isDebug = true;


    public static void init(Context context) {
        XLibrary.context = context;
        screenHeight = XDensityUtils.getScreenHeight();
        screenWidth = XDensityUtils.getScreenWidth();
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

    public static Resources getResources() {
        return XLibrary.getContext().getResources();
    }

    public static String getString(@StringRes int id) {
        return getResources().getString(id);
    }

    public static Resources.Theme getTheme() {
        return XLibrary.getContext().getTheme();
    }

    public static AssetManager getAssets() {
        return XLibrary.getContext().getAssets();
    }

    public static Drawable getDrawable(@DrawableRes int id) {
        return XOutdatedUtils.getDrawable(id);
    }

    public static int getColor( @ColorRes int id) {
        return XOutdatedUtils.getColor(id);
    }

    public static Object getSystemService(String name){
        return XLibrary.getContext().getSystemService(name);
    }

    public static Configuration getConfiguration() {
        return XLibrary.getResources().getConfiguration();
    }

    public static DisplayMetrics getDisplayMetrics() {
        return XLibrary.getResources().getDisplayMetrics();
    }

}
