package com.cb.xlibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;

import com.cb.xlibrary.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * author : caobin
 * e-mail : 821651400@qq.com
 * time   : 2017/12/18
 * desc   :验证码倒计时按钮
 */
public class XCountdownButton extends android.support.v7.widget.AppCompatButton {

    private Handler handler = new Handler();
    private TimerTask mTimerTask;
    private Timer mTimer;
    private int count = 60;
    private String startText = "获取验证码", endText = "重新获取", postfix = "s后重新获取";
    private boolean isCountDowning = false;

    public String getPostfix() {
        return postfix;
    }

    public void setPostfix(String postfix) {
        this.postfix = postfix;
    }

    public String getStartText() {
        return startText;
    }

    public void setStartText(String startText) {
        this.startText = startText;
    }

    public String getEndText() {
        return endText;
    }

    public void setEndText(String endText) {
        this.endText = endText;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        if (isCountDowning) return;
        this.count = count;
    }

    public XCountdownButton(Context context) {
        this(context, null);
    }

    public XCountdownButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XCountdownButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 初始化
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.XCountdownButton);
        this.count = typedArray.getInt(R.styleable.XCountdownButton_countdown, 60);
        this.endText = typedArray.getString(R.styleable.XCountdownButton_endText);
        this.startText = typedArray.getString(R.styleable.XCountdownButton_startText);
        this.postfix = typedArray.getString(R.styleable.XCountdownButton_postfix);
        typedArray.recycle();
        setText(startText);
    }

    public void start() {
        if (isCountDowning) return;
        setClickable(false);
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            int timeCount = count;

            @Override
            public void run() {
                if (timeCount <= 0) {
                    mTimer.cancel();//取消定时器
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            setText(endText);
                        }
                    });
                    setClickable(true);
                    isCountDowning = false;
                    return;
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        setText(timeCount + postfix);
                        timeCount--;
                    }
                });
            }
        };
        mTimer.schedule(mTimerTask, 0, 1000);
    }

    /**
     * 结束mTimer
     */
    public void cancel() {
        if (handler != null) handler.removeCallbacksAndMessages(null);
    }
}
