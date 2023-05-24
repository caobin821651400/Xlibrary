package com.example.cb.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import cn.sccl.xlibrary.utils.XLogUtils;

/**
 * @author: bincao2
 * @date: 2021/11/17 9:51
 * @desc: 描述
 * @updateUser: 更新者
 * @updateDate: 2021/11/17 9:51
 * @updateRemark: 更新说明
 */
public class TestView extends View {
    public TestView(Context context) {
        this(context, null);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        XLogUtils.e(" paddingbottom " + getPaddingBottom());

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int mode = MeasureSpec.getMode(widthMeasureSpec);

        String result = "";
        if (mode == MeasureSpec.AT_MOST) {
            result = "AT_MOST";
        }
        if (mode == MeasureSpec.UNSPECIFIED) {
            result = "UNSPECIFIED";
        }
        if (mode == MeasureSpec.EXACTLY) {
            result = "EXACTLY";
        }


        XLogUtils.d("caobin  mode=" + result);
        XLogUtils.d("caobin  size=" + MeasureSpec.getSize(widthMeasureSpec));
    }
}
