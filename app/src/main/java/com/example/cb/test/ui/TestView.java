package com.example.cb.test.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.cb.xlibrary.utils.XLogUtils;

/**
 * 描述：
 * 作者：曹斌
 * date:2018/9/5 15:57
 */
public class TestView extends android.support.v7.widget.AppCompatButton {
    public TestView(Context context) {
        this(context, null);
    }

    public TestView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 返回 false 时间不响下传递
     * 返回 true 时间响下传递
     * 如果 ACTION_DOWN没有返回true则 ACTION_MOVE，ACTION_UP，ACTION_CANCEL时间都收不到
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        XLogUtils.v("view  onTouchEvent");
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                XLogUtils.e("onTouchEvent: ======ACTION_DOWN");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                XLogUtils.e("onTouchEvent: ======ACTION_MOVE");
//                break;
//            case MotionEvent.ACTION_UP:
//                XLogUtils.e("onTouchEvent: ======ACTION_UP");
//                break;
//            case MotionEvent.ACTION_CANCEL:
//                XLogUtils.e("onTouchEvent: ======ACTION_CANCEL");
//                break;
//            default:
//                XLogUtils.e("onTouchEvent: ======default");
//                break;
//        }
        return super.onTouchEvent(event);
    }

}
