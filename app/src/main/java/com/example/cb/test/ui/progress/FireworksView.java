package com.example.cb.test.ui.progress;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.Random;

/**
 * @author cb
 * @date 2025/1/17
 */
public class FireworksView extends View {

    private final DisplayMetrics mDM;
    private TextPaint mArcPaint;
    private long displayTime = 500L;
    private long clockTime = 0;
    private TextPaint mDrawerPaint = null;
    private Random random;

    final int maxStartNum = 200;
    Star[] stars = new Star[maxStartNum];
    private boolean isRefresh = true;


    public static final int TYPE_BASE = 1;
    public static final int TYPE_FILL_ARC = 2;
    public static final int TYPE_RECT = 3;
    public static final int TYPE_CIRCLE_LINE = 4;
    public static final int TYPE_FILL_ZONE_ON_PATH = 5;
    public static final int TYPE_FILL_LINE = 6;

    private int mType=TYPE_FILL_ZONE_ON_PATH;


    public FireworksView(Context context) {
        this(context, null);
    }

    public FireworksView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FireworksView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDM = getResources().getDisplayMetrics();
        initPaint();

        setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {
                startPlay();
            }
        });
    }


    public static int argb(float red, float green, float blue) {
        return ((int) (1 * 255.0f + 0.5f) << 24) |
                ((int) (red * 255.0f + 0.5f) << 16) |
                ((int) (green * 255.0f + 0.5f) << 8) |
                (int) (blue * 255.0f + 0.5f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        if (widthMode != MeasureSpec.EXACTLY) {
            widthSize = mDM.widthPixels / 2;
        }

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (heightMode != MeasureSpec.EXACTLY) {
            heightSize = widthSize / 2;
        }
        random = new Random(SystemClock.uptimeMillis());

        setMeasuredDimension(widthSize, heightSize);
    }


    public float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mDM);
    }

    public float sp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp, mDM);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        if (width <= 10 || height <= 10) {
            return;
        }
        int saveCount = canvas.save();

        int maxRadius = Math.min(width, height) / 2;
        canvas.translate(width / 2, height / 2);

        long clockTime = getClockTime();
        if (isRefresh) {
            final float dt = 1000;
            final float r = 4;

            for (int i = 0; i < maxStartNum; i++) {

                int t = i % 12;
                double degree = random.nextFloat() * 30 + t * 30;  // 12等分圆

                float minRadius = maxRadius * 1f / 4f;

                double radians = Math.toRadians(degree);
                int radius = (int) (random.nextFloat() * maxRadius * 3f / 4f);

                float x = (float) (Math.cos(radians) * (radius + minRadius));
                float y = (float) (Math.sin(radians) * (radius + minRadius));

                float speedX = (x - 0) / dt;
                float speedY = (y - 0) / dt;

                int color = argb(random.nextFloat(), random.nextFloat(), random.nextFloat());
                if (stars[i] == null) {
                    stars[i] = new Star(speedX, speedY, clockTime, r, radians, color, false, mType);
                } else {
                    stars[i].init(speedX, speedY, clockTime, r, radians, color, false, mType);
                }
            }
            isRefresh = false;
        }

        for (int i = 0; i < maxStartNum; i++) {
            Star star = stars[i];
            star.draw(canvas, mDrawerPaint, clockTime);
        }
        canvas.restoreToCount(saveCount);
    }


    private long getClockTime() {
        return clockTime;
    }

    ValueAnimator animator;

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void startPlay() {
        clockTime = 0;
        isRefresh = true;
        postInvalidate();
        if (animator != null) {
            animator.cancel();
        }
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                clockTime = animation.getCurrentPlayTime();
                postInvalidate();
            }
        });
        valueAnimator.setDuration(displayTime);
        animator = valueAnimator;
        valueAnimator.start();
    }


    private void initPaint() {
        // 实例化画笔并打开抗锯齿
        mArcPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);

        mDrawerPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mDrawerPaint.setAntiAlias(true);
        mDrawerPaint.setStyle(Paint.Style.FILL);
        mDrawerPaint.setStrokeCap(Paint.Cap.ROUND);

    }

    static class Star {
        boolean fromCenter;
        int color;
        double radiansDegree;
        float radius;
        float speedX;
        float speedY;
        long startTime;
        int type = TYPE_FILL_ARC;
        private Path path = new Path();


        public Star(float speedX, float speedY, long clockTime, float r, double radians, int color, boolean fromCenter, int type) {
            init(speedX, speedY, clockTime, r, radians,color,fromCenter,type);
        }


        public void init(float speedX, float speedY, long clockTime, float r, double radians, int color, boolean fromCenter, int type) {
            this.speedX = speedX;
            this.speedY = speedY;
            this.startTime = clockTime;
            this.radius = r;
            this.radiansDegree = radians;
            this.fromCenter = fromCenter;
            this.color = color;
            this.type = type;
        }

        public void draw(Canvas canvas, Paint paint, long clockTime) {

            switch (type) {
                case TYPE_BASE:
                    drawBase(canvas, paint, clockTime);
                    break;
                case TYPE_RECT:
                    drawRect(canvas, paint, clockTime);
                    break;
                case TYPE_CIRCLE_LINE:
                    drawCircleLine(canvas, paint, clockTime);
                    break;
                case TYPE_FILL_LINE:
                    drawFillZone(canvas, paint, clockTime);
                    break;
                case TYPE_FILL_ARC:
                    drawArcFillZone(canvas, paint, clockTime);
                    break;
                case TYPE_FILL_ZONE_ON_PATH:
                    drawFillZoneUsePath(canvas, paint, clockTime);
                    break;
            }

        }

        public void drawFillZone(Canvas canvas, Paint paint, long clockTime) {
            long costTime = clockTime - startTime;
            float dx = 0 + speedX * costTime;
            float dy = 0 + speedY * costTime;

            double currentRadius = Math.sqrt(dx * dx + dy * dy);
            double tail = currentRadius - 20;
            double cosM = Math.cos(radiansDegree);
            double sinM = Math.sin(radiansDegree);

            float cx;
            float cy;

            if (fromCenter || tail < 0) {
                cx = 0f;
                cy = 0f;
            } else {
                cx = (float) (tail * cosM);
                cy = (float) (tail * sinM);
            }

            paint.setColor(color);
            paint.setStrokeWidth(radius);
            canvas.drawLine(cx, cy, dx, dy, paint);
        }

        public void drawArcFillZone(Canvas canvas, Paint paint, long clockTime) {
            long costTime = clockTime - startTime;
            if (costTime == 0) {
                return;
            }
            float dx = 0 + speedX * costTime;
            float dy = 0 + speedY * costTime;

            float currentRadius = (float) Math.sqrt(dx * dx + dy * dy);

            paint.setColor(color);

            int save = canvas.save();

            float degrees = (float) Math.toDegrees(radiansDegree);
            canvas.rotate(degrees);
            // canvas.drawRect(new RectF(currentRadius - 20,-r/2,currentRadius,r/2),paint);
            canvas.drawArc(new RectF(currentRadius - 50, -2 * radius, currentRadius, 2 * radius), -15, 30, true, paint);
            canvas.restoreToCount(save);

        }


        public void drawFillZoneUsePath(Canvas canvas, Paint paint, long clockTime) {
            long costTime = clockTime - startTime;

            float dx = speedX * costTime;
            float dy = speedY * costTime;

            double currentRadius = Math.sqrt(dx * dx + dy * dy);
            path.reset();

            if (currentRadius > 0) {
                if (fromCenter) {
                    path.moveTo(0, 0);
                } else {
                    path.moveTo(dx / 3, dy / 3);
                }

                //1、利用反三角函数计算出小圆切线与所有小圆原点与（0，0）点的夹角
                double asin = Math.asin(radius / currentRadius);

                //2、计算出切线长度
                double aspectRadius = Math.abs(Math.cos(asin) * currentRadius);

                float axLeft = (float) (aspectRadius * Math.cos(radiansDegree - asin));
                float ayLeft = (float) (aspectRadius * Math.sin(radiansDegree - asin));
                path.lineTo(axLeft, ayLeft);

                float axRight = (float) (aspectRadius * Math.cos(radiansDegree + asin));
                float ayRight = (float) (aspectRadius * Math.sin(radiansDegree + asin));

                float cx = (float) (Math.cos(radiansDegree) * (currentRadius + 2 * radius));
                float cy = (float) (Math.sin(radiansDegree) * (currentRadius + 2 * radius));
                //如果使用三角函数计算切线可能很复杂，这里使用贝塞尔曲线简化逻辑
                path.quadTo(cx, cy, axRight, ayRight);
                path.lineTo(axRight, ayRight);

            }
            path.close();
            paint.setColor(color);
            canvas.drawPath(path, paint);
        }

        public void drawCircleLine(Canvas canvas, Paint paint, long clockTime) {
            long costTime = clockTime - startTime;

            if (costTime == 0) {
                return;
            }

            float dx = speedX * costTime;
            float dy = speedY * costTime;
            double currentRadius = Math.sqrt(dx * dx + dy * dy);
            paint.setColor(color);
            if (currentRadius > 0) {
                float cx;
                float cy;
                if (fromCenter) {
                    cx = 0;
                    cy = 0;
                } else {
                    cx = dx / 3;
                    cy = dy / 3;
                }

                //1、利用反三角函数计算出小圆切线与所有小圆原点与（0，0）点的夹角
                double asin = Math.asin(radius / currentRadius);

                //2、计算出切线长度
                double aspectRadius = Math.abs(Math.cos(asin) * currentRadius);
                //左侧坐标
                float axLeft = (float) (aspectRadius * Math.cos(radiansDegree - asin));
                float ayLeft = (float) (aspectRadius * Math.sin(radiansDegree - asin));
                canvas.drawLine(cx, cy, axLeft, ayLeft, paint);
                float axRight = (float) (aspectRadius * Math.cos(radiansDegree + asin));
                float ayRight = (float) (aspectRadius * Math.sin(radiansDegree + asin));
                canvas.drawLine(cx, cy, axRight, ayRight, paint);

            }
            canvas.drawCircle(dx, dy, radius, paint);
        }


        public void drawBase(Canvas canvas, Paint paint, long clockTime) {
            long costTime = clockTime - startTime;

            float dx = speedX * costTime;
            float dy = speedY * costTime;

            double currentRadius = Math.sqrt(dx * dx + dy * dy);

            paint.setColor(color);

            if (currentRadius > 0) {
                double asin = Math.asin(radius / currentRadius);
                //利用反三角函数计算出切线与圆的夹角
                int t = 1;
                for (int i = 0; i < 2; i++) {
                    double aspectRadius = Math.abs(Math.cos(asin) * currentRadius);  //切线长度
                    float ax = (float) (aspectRadius * Math.cos(radiansDegree + asin * t));
                    float ay = (float) (aspectRadius * Math.sin(radiansDegree + asin * t));
                    if (fromCenter) {
                        canvas.drawLine(0, 0, ax, ay, paint);
                    } else {
                        canvas.drawLine(dx / 3, dy / 3, ax, ay, paint);
                    }
                    t = -1;
                }

            }
            canvas.drawCircle(dx, dy, radius, paint);
        }

        //使用Path如果粒子多的化性能会极速下降
        public void drawCircleLineUsePath(Canvas canvas, Paint paint, long clockTime) {
            long costTime = clockTime - startTime;

            float dx = speedX * costTime;
            float dy = speedY * costTime;

            double currentRadius = Math.sqrt(dx * dx + dy * dy);
            path.reset();

            if (currentRadius > 0) {

                if (fromCenter) {
                    path.moveTo(0, 0);
                } else {
                    path.moveTo(dx / 3, dy / 3);
                }

                //1、利用反三角函数计算出小圆切线与所有小圆原点与（0，0）点的夹角
                double asin = Math.asin(radius / currentRadius);

                //2、计算出切线长度
                double aspectRadius = Math.abs(Math.cos(asin) * currentRadius);

                float axLeft = (float) (aspectRadius * Math.cos(radiansDegree - asin));
                float ayLeft = (float) (aspectRadius * Math.sin(radiansDegree - asin));
                path.lineTo(axLeft, ayLeft);

                float axRight = (float) (aspectRadius * Math.cos(radiansDegree + asin));
                float ayRight = (float) (aspectRadius * Math.sin(radiansDegree + asin));
                path.lineTo(axRight, ayRight);
                path.addCircle(dx, dy, radius, Path.Direction.CCW);

            }
            path.close();
            paint.setColor(color);
            canvas.drawPath(path, paint);
        }


        public void drawRect(Canvas canvas, Paint paint, long clockTime) {
            long costTime = clockTime - startTime;

            float dx = speedX * costTime;
            float dy = speedY * costTime;

            paint.setColor(color);
            //  RectF rectF = new RectF(dx - radius, dy - radius, dx + radius, dy + radius);
            // canvas.drawRect(rectF, paint);
            canvas.drawCircle(dx,dy,radius,paint);

        }
    }
}
