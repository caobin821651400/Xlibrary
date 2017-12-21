package com.cb.xlibrary.utils.log;


import android.text.TextUtils;

import com.cb.xlibrary.XLibrary;


public class XLogConfig {

    private boolean showThreadInfo = true;
    private boolean debug = XLibrary.isDebug;
    private String tag = XLibrary.tag;


    public XLogConfig setTag(String tag) {
        if (!TextUtils.isEmpty(tag)) {
            this.tag = tag;
        }
        return this;
    }

    public XLogConfig setShowThreadInfo(boolean showThreadInfo) {
        this.showThreadInfo = showThreadInfo;
        return this;
    }


    public XLogConfig setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public boolean isDebug() {
        return debug;
    }

    public boolean isShowThreadInfo() {
        return showThreadInfo;
    }
}
