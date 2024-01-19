package com.example.cb.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import cn.sccl.xlibrary.utils.XLogUtils;


/**
 * @author: cb
 * @date: 2024/1/19 09:33
 * @desc: 描述
 */
public class Aa extends View {
    public Aa(Context context) {
        super(context);
    }

    public Aa(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Aa(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean b = super.dispatchTouchEvent(ev);
        XLogUtils.v("caobin 33333333333=" + b);
        return b;
    }
}
