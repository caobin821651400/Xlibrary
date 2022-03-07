package com.example.cb.test.view.font;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.List;

/**
 * @author: bincao2
 * @date: 2022/3/4 17:00
 * @desc: 描述
 * @updateUser: 更新者
 * @updateDate: 2022/3/4 17:00
 * @updateRemark: 更新说明
 */
public class GradientTextView extends TextView {

    private LinearGradient mLinearGradient;
    private Paint mPaint;
    private int mViewWidth = 0;//文字的宽度
    private int mViewHeight = 0;//文字的高度
    private Rect mTextBound = new Rect();
    private int[] mColorList;//存放颜色的数组
    private boolean isVertrial;//默认是横向

    public GradientTextView(Context context) {
        this(context, null);
    }

    public GradientTextView(Context context,
                            AttributeSet attrs) {
        super(context, attrs);
        //设置默认的颜色
        mColorList = new int[]{0xFFffff40, 0xFFff6027};
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    //绘制的矩形区域
    private RectF mRectF;

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        mRectF = new RectF(0, 0, width, height);
//        mPaint.setShader(new LinearGradient(0, 0, mRectF.right, 0,
//                mStartColor, mEndColor, Shader.TileMode.MIRROR));
    }
    //默认渐变色开始颜色(红色)
    private static final int DEFAULT_START_COLOR = Color.parseColor("#FF0000");
    //默认渐变色结束颜色(黄色)
    private static final int DEFAULT_END_COLOR = Color.parseColor("#FFFF00");

    @Override
    protected void onDraw(Canvas canvas) {

        if (isVertrial) {
            mViewHeight = getMeasuredHeight();
        } else {
            mViewWidth = getMeasuredWidth();
        }
//        mPaint = getPaint();
        mPaint.setTextSize(getTextSize()+10);
        String mTipText = getText().toString();
        mPaint.getTextBounds(mTipText, 0, mTipText.length(), mTextBound);
        //前面4个参数分别表示渐变的开始x轴,开始y轴,结束的x轴,结束的y轴,mcolorList表示渐变的颜色数组
        mLinearGradient = new LinearGradient(0, 0, 0, mRectF.bottom, DEFAULT_START_COLOR, DEFAULT_END_COLOR, Shader.TileMode.MIRROR);
        mPaint.setShader(mLinearGradient);
        //画出文字
        canvas.drawText(mTipText, getMeasuredWidth() / 2 - mTextBound.width() / 2-10, getMeasuredHeight() / 2 + mTextBound.height() / 2, mPaint);

        super.onDraw(canvas);
    }

    /**
     * true表示纵向渐变,false变身横向渐变
     *
     * @param vertrial
     */
    public void setVertrial(boolean vertrial) {
        isVertrial = vertrial;
    }

    /**
     * 设置渐变的颜色
     *
     * @param mColorList
     */
    public void setmColorList(int[] mColorList) {
        if (mColorList != null && mColorList.length < 2) {
            throw new RuntimeException("mClorList's length must be > 2");
        } else {

            this.mColorList = mColorList;
        }
    }
}
