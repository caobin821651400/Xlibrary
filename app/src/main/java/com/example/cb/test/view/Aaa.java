package com.example.cb.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import cn.sccl.xlibrary.utils.XLogUtils;


/**
 * @author: cb
 * @date: 2024/1/19 09:40
 * @desc: 描述
 */
public class Aaa extends FrameLayout {


    public Aaa(Context context) {
        super(context);
    }

    public Aaa(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Aaa(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View Child = getChildAt(0);
        Child.layout(0, 0, Child.getMeasuredWidth(), Child.getMeasuredHeight());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean b = super.dispatchTouchEvent(ev);
        XLogUtils.v("caobin 111111111111111=" + b);
        return b;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean b = super.onInterceptTouchEvent(ev);
        XLogUtils.v("caobin 2222222222222222=" + b);
        return b;
    }
}
