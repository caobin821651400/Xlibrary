package com.example.cb.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * 梯形textView
 */
public class TrapezoidTextView extends android.support.v7.widget.AppCompatTextView {

    private Context mContext;
    private Paint mPaint;
    private int backgroundColor = Color.RED;
    private int trapezoidSpace = 20;//梯形上边比下边少的距离
    //
    RectF mRectF;

    public TrapezoidTextView(Context context) {
        this(context, null);
    }

    public TrapezoidTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrapezoidTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        Shader mShader = new LinearGradient(0, 500, 0, 0,
//                new int[]{backgroundColor, backgroundColor},
//                null, Shader.TileMode.CLAMP);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
//        mPaint.setShader(mShader);
        mPaint.setColor(backgroundColor);
        mPaint.setStrokeWidth(3f);
        mPaint.setStyle(Paint.Style.STROKE);
//        setBackgroundColor(Color.YELLOW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();


        Path path1 = new Path();
        mRectF = new RectF(0, 0, width, height);
//        path1.moveTo(0, 0);
        path1.lineTo(width, 0);//上底
        path1.arcTo(mRectF,0,90,false);
        path1.lineTo(width-trapezoidSpace, height);//右侧
        path1.lineTo(trapezoidSpace, height);//底部
        path1.lineTo(0,0);
//        path1.close();
        canvas.drawPath(path1, mPaint);

//        mRectF = new RectF(0, 0, width, height);
//        mRectF = new RectF(0);
//        canvas.drawRoundRect(mRectF,10,10,mPaint);
        super.onDraw(canvas);
    }
}
