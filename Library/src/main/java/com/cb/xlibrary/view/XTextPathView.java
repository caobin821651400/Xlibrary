package com.cb.xlibrary.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.cb.xlibrary.R;
import com.cb.xlibrary.utils.XDensityUtils;


/**
 * 描述：文字路径动态绘制
 * 作者：曹斌
 * date:2018/7/20 10:44
 */
public class XTextPathView extends View {

    private TextPaint mPaint;

    private int mTextSize = 64;
    //文本宽高
    private float mTextWidth;
    private float mTextHeight;
    //颜色
    private int mTextColor = Color.RED;
    //描边宽度
    private float mStrokeWidth = 5;
    //整体path
    private Path mFontPath;
    //截取后的path
    private Path mDstPath;
    //
    private PathMeasure mPathMeasure;
    //
    private float mPathLength;
    private float mCurrentLength;
    //绘画部分长度
    private float mStop = 0;
    //动画
    private ValueAnimator mValueAnimator = null;
    //是否循环播放
    private boolean mIsCycle;
    //动画时长
    private int mDuration = 6000;
    //是否自动开始
    private boolean mIsAutoStart;
    //文本
    private String mText = "Hello";

    public XTextPathView(Context context) {
        this(context, null);
    }

    public XTextPathView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XTextPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        //关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        TypedArray tt = context.obtainStyledAttributes(attrs, R.styleable.XTextPathViewCbLibrary);
        mText = tt.getString(R.styleable.XTextPathViewCbLibrary_text);
        mTextSize = tt.getDimensionPixelSize(R.styleable.XTextPathViewCbLibrary_pathTextSize, 108);
        mTextColor = tt.getColor(R.styleable.XTextPathViewCbLibrary_pathTextColor, Color.RED);
        mDuration = tt.getInt(R.styleable.XTextPathViewCbLibrary_duration, 6000);
        mStrokeWidth = tt.getDimensionPixelOffset(R.styleable.XTextPathViewCbLibrary_strokeWidth, 5);
        mIsCycle = tt.getBoolean(R.styleable.XTextPathViewCbLibrary_cycle, false);
        mIsAutoStart = tt.getBoolean(R.styleable.XTextPathViewCbLibrary_autoStart, true);
        tt.recycle();

        mPaint = new TextPaint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
        mPaint.setStrokeWidth(mStrokeWidth);
        //
        mFontPath = new Path();
        mDstPath = new Path();
        mPathMeasure = new PathMeasure();

        initTextPath();
    }

    /**
     * 初始化文字路径
     */
    private void initTextPath() {
        mPathLength = 0;
        mFontPath.reset();
        mDstPath.reset();
        if (mText == null || mText.equals("")) {
            mText = "Hello";
        }

        mTextWidth = Layout.getDesiredWidth(mText, mPaint);
        Paint.FontMetrics metrics = mPaint.getFontMetrics();
        mTextHeight = metrics.bottom - metrics.top;
        mPaint.getTextPath(mText, 0, mText.length(), 0, -metrics.ascent, mFontPath);
        mPathMeasure.setPath(mFontPath, false);
        mPathLength = mPathMeasure.getLength();
        while (mPathMeasure.nextContour()) {
            mPathLength += mPathMeasure.getLength();
        }

        if (mIsAutoStart) {
            post(new Runnable() {
                @Override
                public void run() {
                    startAnimation();
                }
            });
        }
    }

    /**
     * 开始动画
     */
    public void startAnimation() {
        if (mValueAnimator == null) {
            mValueAnimator = ValueAnimator.ofFloat(0, mPathLength);
        }
        if (mValueAnimator.isRunning()) return;
        if (mIsCycle) {
            //重复动画
            mValueAnimator.setRepeatCount(-1);
        } else {
            mValueAnimator.setRepeatCount(0);
        }
        mValueAnimator.setDuration(mDuration);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentLength = (float) mValueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        mValueAnimator.start();
    }

    /**
     * 停止动画
     */
    public void stopAnimation() {
        if (mValueAnimator != null && mValueAnimator.isRunning())
            mValueAnimator.cancel();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //处理包裹内容的情况
        int wareDefaultSize = XDensityUtils.dp2px(getContext(), 100);
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            widthSize = heightSize = wareDefaultSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = wareDefaultSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = wareDefaultSize;
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        mDstPath.reset();
        mStop = mCurrentLength;

        //画布居中
        canvas.translate(getWidth() / 2 - mTextWidth / 2, getHeight() / 2 - mTextHeight / 2);
//        canvas.translate(0, getHeight() / 2 - mTextHeight / 2);
        //重置路径
        mPathMeasure.setPath(mFontPath, false);
        while (mStop > mPathMeasure.getLength()) {
            mStop = mStop - mPathMeasure.getLength();
            mPathMeasure.getSegment(0, mPathMeasure.getLength(), mDstPath, true);
            if (!mPathMeasure.nextContour()) {//没有下一个轮廓
                break;
            }
        }
        mPathMeasure.getSegment(0, mStop, mDstPath, true);
        canvas.drawPath(mDstPath, mPaint);
        canvas.restore();
    }

    /**
     * 设置画笔宽度
     *
     * @param size
     * @return
     */
    public XTextPathView setTextSize(int size) {
        this.mTextSize = size;
        this.mPaint.setTextSize(mTextSize);
        return this;
    }

    /**
     * 设置画笔颜色
     *
     * @param color
     * @return
     */
    public XTextPathView setTextColor(int color) {
        this.mTextColor = color;
        this.mPaint.setColor(mTextColor);
        return this;
    }

    /**
     * 设置绘制的文本
     *
     * @param text
     * @return
     */
    public XTextPathView setText(String text) {
        this.mText = text;
        this.initTextPath();
        return this;
    }

    /**
     * 设置动画时长
     *
     * @param duration
     * @return
     */
    public XTextPathView setDuration(int duration) {
        this.mDuration = duration;
        return this;
    }

    /**
     * 设置描边宽度
     *
     * @param width
     * @return
     */
    public XTextPathView setStrokeWidth(int width) {
        this.mStrokeWidth = width;
        this.mPaint.setStrokeWidth(mStrokeWidth);
        return this;
    }

    //是否循环
    public XTextPathView setCycle(boolean cycle) {
        this.mIsCycle = cycle;
        return this;
    }

    //是否自动开始
    public XTextPathView setAutoStart(boolean autoStart) {
        this.mIsAutoStart = autoStart;
        return this;
    }

    public int getTextSize() {
        return mTextSize;
    }

    public int getTextColor() {
        return mTextColor;
    }

    public String getText() {
        return mText;
    }

    public float getStrokeWidth() {
        return mStrokeWidth;
    }

    public int getDuration() {
        return mDuration;
    }

    public boolean isCycle() {
        return mIsCycle;
    }

    public boolean isAutoStart() {
        return mIsAutoStart;
    }
}
