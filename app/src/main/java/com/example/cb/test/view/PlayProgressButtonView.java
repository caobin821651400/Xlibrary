package com.example.cb.test.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.cb.test.R;

/**
 * @author: bincao2
 * @date: 2021/11/17 9:51
 * @desc: 描述
 * @updateUser: 更新者
 * @updateDate: 2021/11/17 9:51
 * @updateRemark: 更新说明
 */
public class PlayProgressButtonView extends View {

    private static final int MAX_PROGRESS = 100;
    private Paint mPaint;
    private int mStrokeColor, mProgressColor;
    private int mStartAngle, mCurrentProgress;
    private float mStrokeWidth;
    private RectF mProgressRectF;
    private float mCenter, mRadius;
    private Drawable mPlayDrawable, mPauseDrawable;
    private Rect mBitmapRect;
    private Bitmap mCurrBitmap;

    public PlayProgressButtonView(Context context) {
        this(context, null);
    }

    public PlayProgressButtonView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayProgressButtonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.PlayProgressButtonView);
        mStrokeColor = mTypedArray.getColor(R.styleable.PlayProgressButtonView_ppbv_stroke_color, 0xff50c0e9);
        mProgressColor = mTypedArray.getColor(R.styleable.PlayProgressButtonView_ppbv_progress_color, 0xffffc641);
        mStartAngle = mTypedArray.getInt(R.styleable.PlayProgressButtonView_ppbv_start_angle, -90);
        mStrokeWidth = mTypedArray.getDimension(R.styleable.PlayProgressButtonView_ppbv_stroke_width, 1f);
        mPlayDrawable = mTypedArray.getDrawable(R.styleable.PlayProgressButtonView_ppbv_play_drawable);
        mPauseDrawable = mTypedArray.getDrawable(R.styleable.PlayProgressButtonView_ppbv_pause_drawable);

        //这两个资源不能为空
        if (mPlayDrawable == null || mPauseDrawable == null) {
            throw new RuntimeException("mPlayDrawable and mPauseDrawable cannot be empty");
        }

        if (mPlayDrawable instanceof BitmapDrawable) {
            mCurrBitmap = ((BitmapDrawable) mPlayDrawable).getBitmap();
        }
        //回收资源
        mTypedArray.recycle();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //获取圆心x坐标
        mCenter = getWidth() / 2.0f;
        //圆环的半径
        mRadius = (int) (mCenter - mStrokeWidth / 2.0f);
        mProgressRectF = new RectF(mCenter - mRadius, mCenter - mRadius, mCenter + mRadius, mCenter + mRadius);

        int centerHalf = (int) (mCenter / 2);
        mBitmapRect = new Rect(centerHalf, centerHalf, getWidth() - centerHalf, getHeight() - centerHalf);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画外面大圆环
        mPaint.setColor(mStrokeColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setAntiAlias(true);
        canvas.drawCircle(mCenter, mCenter, mRadius, mPaint);

        //画圆弧进度
        if (mCurrentProgress != 0) {
            mPaint.setColor(mProgressColor);
            mPaint.setStrokeWidth(mStrokeWidth);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawArc(mProgressRectF, mStartAngle,
                    360 * mCurrentProgress * 1.0f / MAX_PROGRESS, false, mPaint);
        }

        //画中间的按钮
        if (mCurrBitmap != null) {
            canvas.drawBitmap(mCurrBitmap, null, mBitmapRect, mPaint);
        }
    }

    /**
     * 更新进度
     *
     * @param progress
     */
    public void updateProgress(int progress) {
        if (progress > MAX_PROGRESS) {
            return;
        }
        mCurrentProgress = progress;
        invalidate();
    }

    /**
     * 暂停
     */
    public void pause() {
        mCurrBitmap = ((BitmapDrawable) mPlayDrawable).getBitmap();
        invalidate();
    }

    /**
     * 播放
     */
    public void play() {
        mCurrBitmap = ((BitmapDrawable) mPauseDrawable).getBitmap();
        invalidate();
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mCurrBitmap != null && !mCurrBitmap.isRecycled()) {
            mCurrBitmap.recycle();
            mCurrBitmap = null;
        }
    }
}
