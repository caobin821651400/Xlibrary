package com.example.cb.test.hook;

import cb.xlibrary.utils.XLogUtils;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2020/6/17 14:21
 * @Desc :用于HOOK的对象
 * ====================================================
 */
public class HookObject {


    private static int mGroupFlags = 0;


    public void prlin() {
        XLogUtils.e("打印-->" + mGroupFlags);
    }
}
