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

    private static final int mGroupFlags = 0;

    public int getConstant() {
        return mGroupFlags;
    }


    public void hookMethod() {
        XLogUtils.d("无参数public方法调用了");
    }

    private void hookMethod2() {
        XLogUtils.d("无参数private方法调用了");
    }

    protected void hookMethod3(int i) {
        XLogUtils.d("有参protected方法调用了;  参数(int类型)：" + i);
    }


    private String hookMethod4(String s) {
        XLogUtils.d("有参有返回值private方法调用了;  参数：(String类型)：" + s);
        return s + "~~";
    }

    private String hookMethod5() {
        XLogUtils.d("无参有返回值private方法调用了");
        return "我是返回值";
    }
}
