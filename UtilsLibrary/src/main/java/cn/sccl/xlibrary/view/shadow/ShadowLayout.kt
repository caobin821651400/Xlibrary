package cn.sccl.xlibrary.view.shadow;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.cb.xlibrary.R;

/**
 * Created by leo
 * on 2019/7/9.
 * 阴影控件
 */

public class ShadowLayout extends FrameLayout {
    /***
     * Shadow
     */
    private Paint shadowPaint;//阴影画笔
    private int mShadowColor;//阴影颜色
    private float mShadowLimit = 5;//阴影扩散区域大小
    //阴影x,y偏移量
    private float mDx;
    private float mDy;
    //阴影圆角属性，也是shape的圆角属性
    private float mCornerRadius;//圆角大小，如四角没有单独设置，则为大小为mCornerRadius
    private float mCornerRadius_leftTop;//单独设置左上角圆角大小。（同理以下）
    private float mCornerRadius_rightTop;
    private float mCornerRadius_leftBottom;
    private float mCornerRadius_rightBottom;
    //阴影四边可见属性
    private boolean leftShow;//false表示左边不可见
    private boolean rightShow;
    private boolean topShow;
    private boolean bottomShow;
    //子布局与父布局的padding（即通过padding来实现mShadowLimit的大小和阴影展示）
    private int leftPadding;
    private int topPadding;
    private int rightPadding;
    private int bottomPadding;
    private RectF rectf = new RectF();//阴影布局子空间区域
    private View firstView;//如有子View则为子View，否则为ShadowLayout本身
    //
    private boolean isSym;//控件区域是否对称，如不对称则区域跟随阴影走

    private int defaultShadowColor = Color.parseColor("#2a000000");

    public ShadowLayout(Context context) {
        this(context, null);
    }


    public ShadowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public ShadowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }


    private void initView(Context context, AttributeSet attrs) {
        initAttributes(attrs);
        shadowPaint = new Paint();
        shadowPaint.setAntiAlias(true);
        shadowPaint.setStyle(Paint.Style.FILL);
        setPadding();
    }


    private void initAttributes(AttributeSet attrs) {
        TypedArray attr = getContext().obtainStyledAttributes(attrs, R.styleable.ShadowLayout);

        leftShow = !attr.getBoolean(R.styleable.ShadowLayout_hl_shadowHiddenLeft, false);
        rightShow = !attr.getBoolean(R.styleable.ShadowLayout_hl_shadowHiddenRight, false);
        bottomShow = !attr.getBoolean(R.styleable.ShadowLayout_hl_shadowHiddenBottom, false);
        topShow = !attr.getBoolean(R.styleable.ShadowLayout_hl_shadowHiddenTop, false);
        mCornerRadius = attr.getDimension(R.styleable.ShadowLayout_hl_cornerRadius, 0);
        mCornerRadius_leftTop = attr.getDimension(R.styleable.ShadowLayout_hl_cornerRadius_leftTop, -1);
        mCornerRadius_leftBottom = attr.getDimension(R.styleable.ShadowLayout_hl_cornerRadius_leftBottom, -1);
        mCornerRadius_rightTop = attr.getDimension(R.styleable.ShadowLayout_hl_cornerRadius_rightTop, -1);
        mCornerRadius_rightBottom = attr.getDimension(R.styleable.ShadowLayout_hl_cornerRadius_rightBottom, -1);

        //默认扩散区域宽度
        mShadowLimit = attr.getDimension(R.styleable.ShadowLayout_hl_shadowLimit, 5);

        //x轴偏移量
        mDx = attr.getDimension(R.styleable.ShadowLayout_hl_shadowOffsetX, 0);
        //y轴偏移量
        mDy = attr.getDimension(R.styleable.ShadowLayout_hl_shadowOffsetY, 0);
        mShadowColor = attr.getColor(R.styleable.ShadowLayout_hl_shadowColor, defaultShadowColor);

        isSym = attr.getBoolean(R.styleable.ShadowLayout_hl_shadowSymmetry, true);
        attr.recycle();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0) {
            setBackgroundCompat(w, h);
        }
    }


    /**
     * 被加载到页面后触发。也就是执行Inflater.inflate()方法后
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        firstView = getChildAt(0);
        if (firstView == null) {
            firstView = ShadowLayout.this;
        }
    }


    /**
     * 对子View进行绘制，也是剪裁子view的关键
     *
     * @param canvas
     */

    @Override
    protected void dispatchDraw(Canvas canvas) {
        int trueHeight = (int) (rectf.bottom - rectf.top);
        if (getChildAt(0) != null) {
            if (mCornerRadius_leftTop == -1 && mCornerRadius_leftBottom == -1 && mCornerRadius_rightTop == -1 && mCornerRadius_rightBottom == -1) {
                //说明没有设置过任何特殊角、且是半圆。
                if (mCornerRadius > trueHeight / 2) {
                    Path path = new Path();
                    path.addRoundRect(rectf, trueHeight / 2, trueHeight / 2, Path.Direction.CW);
                    canvas.clipPath(path);
                } else {
                    Path path = new Path();
                    path.addRoundRect(rectf, mCornerRadius, mCornerRadius, Path.Direction.CW);
                    canvas.clipPath(path);
                }
            } else {
                float[] outerR = getCornerValue(trueHeight);
                Path path = new Path();
                path.addRoundRect(leftPadding, topPadding, getWidth() - rightPadding, getHeight() - bottomPadding, outerR, Path.Direction.CW);
                canvas.clipPath(path);
            }

        }
        super.dispatchDraw(canvas);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        rectf.left = leftPadding;
        rectf.top = topPadding;
        rectf.right = getWidth() - rightPadding;
        rectf.bottom = getHeight() - bottomPadding;
    }


    private float[] getCornerValue(int trueHeight) {
        int leftTop;
        int rightTop;
        int rightBottom;
        int leftBottom;
        if (mCornerRadius_leftTop == -1) {
            leftTop = (int) mCornerRadius;
        } else {
            leftTop = (int) mCornerRadius_leftTop;
        }

        if (leftTop > trueHeight / 2) {
            leftTop = trueHeight / 2;
        }

        if (mCornerRadius_rightTop == -1) {
            rightTop = (int) mCornerRadius;
        } else {
            rightTop = (int) mCornerRadius_rightTop;
        }

        if (rightTop > trueHeight / 2) {
            rightTop = trueHeight / 2;
        }

        if (mCornerRadius_rightBottom == -1) {
            rightBottom = (int) mCornerRadius;
        } else {
            rightBottom = (int) mCornerRadius_rightBottom;
        }

        if (rightBottom > trueHeight / 2) {
            rightBottom = trueHeight / 2;
        }


        if (mCornerRadius_leftBottom == -1) {
            leftBottom = (int) mCornerRadius;
        } else {
            leftBottom = (int) mCornerRadius_leftBottom;
        }

        if (leftBottom > trueHeight / 2) {
            leftBottom = trueHeight / 2;
        }

        float[] outerR = new float[]{leftTop, leftTop, rightTop, rightTop, rightBottom, rightBottom, leftBottom, leftBottom};//左上，右上，右下，左下
        return outerR;
    }


    private void setPadding() {
        //控件区域是否对称，默认是对称。不对称的话，那么控件区域随着阴影区域走
        if (isSym) {
            int xPadding = (int) (mShadowLimit + Math.abs(mDx));
            int yPadding = (int) (mShadowLimit + Math.abs(mDy));

            if (leftShow) {
                leftPadding = xPadding;
            } else {
                leftPadding = 0;
            }

            if (topShow) {
                topPadding = yPadding;
            } else {
                topPadding = 0;
            }


            if (rightShow) {
                rightPadding = xPadding;
            } else {
                rightPadding = 0;
            }

            if (bottomShow) {
                bottomPadding = yPadding;
            } else {
                bottomPadding = 0;
            }


        } else {
            if (Math.abs(mDy) > mShadowLimit) {
                if (mDy > 0) {
                    mDy = mShadowLimit;
                } else {
                    mDy = 0 - mShadowLimit;
                }
            }


            if (Math.abs(mDx) > mShadowLimit) {
                if (mDx > 0) {
                    mDx = mShadowLimit;
                } else {
                    mDx = 0 - mShadowLimit;
                }
            }

            if (topShow) {
                topPadding = (int) (mShadowLimit - mDy);
            } else {
                topPadding = 0;
            }

            if (bottomShow) {
                bottomPadding = (int) (mShadowLimit + mDy);
            } else {
                bottomPadding = 0;
            }


            if (rightShow) {
                rightPadding = (int) (mShadowLimit - mDx);
            } else {
                rightPadding = 0;
            }


            if (leftShow) {
                leftPadding = (int) (mShadowLimit + mDx);
            } else {
                leftPadding = 0;
            }
        }
        setPadding(leftPadding, topPadding, rightPadding, bottomPadding);
    }


    @SuppressWarnings("deprecation")
    private void setBackgroundCompat(int w, int h) {
        //判断传入的颜色值是否有透明度
        isAddAlpha(mShadowColor);
        Bitmap bitmap = createShadowBitmap(w, h, mCornerRadius, mShadowLimit, mDx, mDy, mShadowColor, Color.TRANSPARENT);
        BitmapDrawable drawable = new BitmapDrawable(bitmap);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
            setBackgroundDrawable(drawable);
        } else {
            setBackground(drawable);
        }
    }


    private Bitmap createShadowBitmap(int shadowWidth, int shadowHeight, float cornerRadius, float shadowRadius,
                                      float dx, float dy, int shadowColor, int fillColor) {
        //优化阴影bitmap大小,将尺寸缩小至原来的1/4。
        dx = dx / 4;
        dy = dy / 4;
        shadowWidth = shadowWidth / 4 == 0 ? 1 : shadowWidth / 4;
        shadowHeight = shadowHeight / 4 == 0 ? 1 : shadowHeight / 4;
        cornerRadius = cornerRadius / 4;
        shadowRadius = shadowRadius / 4;

        Bitmap output = Bitmap.createBitmap(shadowWidth, shadowHeight, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(output);

        //这里缩小limit的是因为，setShadowLayer后会将bitmap扩散到shadowWidth，shadowHeight
        //同时也要根据某边的隐藏情况去改变

        float rect_left = 0;
        float rect_right = 0;
        float rect_top = 0;
        float rect_bottom = 0;
        if (leftShow) {
            rect_left = shadowRadius;
        } else {
            float maxLeftTop = Math.max(cornerRadius, mCornerRadius_leftTop);
            float maxLeftBottom = Math.max(cornerRadius, mCornerRadius_leftBottom);
            float maxLeft = Math.max(maxLeftTop, maxLeftBottom);
            float trueMaxLeft = Math.max(maxLeft, shadowRadius);
            rect_left = trueMaxLeft / 2;

        }

        if (topShow) {
            rect_top = shadowRadius;
        } else {
            float maxLeftTop = Math.max(cornerRadius, mCornerRadius_leftTop);
            float maxRightTop = Math.max(cornerRadius, mCornerRadius_rightTop);
            float maxTop = Math.max(maxLeftTop, maxRightTop);
            float trueMaxTop = Math.max(maxTop, shadowRadius);
            rect_top = trueMaxTop / 2;

        }

        if (rightShow) {
            rect_right = shadowWidth - shadowRadius;
        } else {
            float maxRightTop = Math.max(cornerRadius, mCornerRadius_rightTop);
            float maxRightBottom = Math.max(cornerRadius, mCornerRadius_rightBottom);
            float maxRight = Math.max(maxRightTop, maxRightBottom);
            float trueMaxRight = Math.max(maxRight, shadowRadius);
            rect_right = shadowWidth - trueMaxRight / 2;
        }


        if (bottomShow) {
            rect_bottom = shadowHeight - shadowRadius;
        } else {
            float maxLeftBottom = Math.max(cornerRadius, mCornerRadius_leftBottom);
            float maxRightBottom = Math.max(cornerRadius, mCornerRadius_rightBottom);
            float maxBottom = Math.max(maxLeftBottom, maxRightBottom);
            float trueMaxBottom = Math.max(maxBottom, shadowRadius);
            rect_bottom = shadowHeight - trueMaxBottom / 2;
        }


        RectF shadowRect = new RectF(
                rect_left,
                rect_top,
                rect_right,
                rect_bottom);

        if (isSym) {
            if (dy > 0) {
                shadowRect.top += dy;
                shadowRect.bottom -= dy;
            } else if (dy < 0) {
                shadowRect.top += Math.abs(dy);
                shadowRect.bottom -= Math.abs(dy);
            }

            if (dx > 0) {
                shadowRect.left += dx;
                shadowRect.right -= dx;
            } else if (dx < 0) {

                shadowRect.left += Math.abs(dx);
                shadowRect.right -= Math.abs(dx);
            }
        } else {
            shadowRect.top -= dy;
            shadowRect.bottom -= dy;
            shadowRect.right -= dx;
            shadowRect.left -= dx;
        }


        shadowPaint.setColor(fillColor);
        if (!isInEditMode()) {//dx  dy
            shadowPaint.setShadowLayer(shadowRadius / 2, dx, dy, shadowColor);
        }

        if (mCornerRadius_leftBottom == -1 && mCornerRadius_leftTop == -1 && mCornerRadius_rightTop == -1 && mCornerRadius_rightBottom == -1) {
            //如果没有设置整个属性，那么按原始去画
            canvas.drawRoundRect(shadowRect, cornerRadius, cornerRadius, shadowPaint);
        } else {
            //目前最佳的解决方案
            rectf.left = leftPadding;
            rectf.top = topPadding;
            rectf.right = getWidth() - rightPadding;
            rectf.bottom = getHeight() - bottomPadding;


            shadowPaint.setAntiAlias(true);
            int leftTop;
            if (mCornerRadius_leftTop == -1) {
                leftTop = (int) mCornerRadius / 4;
            } else {
                leftTop = (int) mCornerRadius_leftTop / 4;
            }
            int leftBottom;
            if (mCornerRadius_leftBottom == -1) {
                leftBottom = (int) mCornerRadius / 4;
            } else {
                leftBottom = (int) mCornerRadius_leftBottom / 4;
            }

            int rightTop;
            if (mCornerRadius_rightTop == -1) {
                rightTop = (int) mCornerRadius / 4;
            } else {
                rightTop = (int) mCornerRadius_rightTop / 4;
            }

            int rightBottom;
            if (mCornerRadius_rightBottom == -1) {
                rightBottom = (int) mCornerRadius / 4;
            } else {
                rightBottom = (int) mCornerRadius_rightBottom / 4;
            }

            float[] outerR = new float[]{leftTop, leftTop, rightTop, rightTop, rightBottom, rightBottom, leftBottom, leftBottom};//左上，右上，右下，左下
            Path path = new Path();
            path.addRoundRect(shadowRect, outerR, Path.Direction.CW);
            canvas.drawPath(path, shadowPaint);
        }

        return output;
    }


    private void isAddAlpha(int color) {
        //获取单签颜色值的透明度，如果没有设置透明度，默认加上#2a
        if (Color.alpha(color) == 255) {
            String red = Integer.toHexString(Color.red(color));
            String green = Integer.toHexString(Color.green(color));
            String blue = Integer.toHexString(Color.blue(color));

            if (red.length() == 1) {
                red = "0" + red;
            }

            if (green.length() == 1) {
                green = "0" + green;
            }

            if (blue.length() == 1) {
                blue = "0" + blue;
            }
            String endColor = "#2a" + red + green + blue;
            mShadowColor = convertToColorInt(endColor);
        }
    }


    private static int convertToColorInt(String argb)
            throws IllegalArgumentException {

        if (!argb.startsWith("#")) {
            argb = "#" + argb;
        }

        return Color.parseColor(argb);
    }

    /**
     * 设置x轴阴影的偏移量
     *
     * @param mDx
     */
    public ShadowLayout setShadowOffsetX(float mDx) {
        if (Math.abs(mDx) > mShadowLimit) {
            if (mDx > 0) {
                this.mDx = mShadowLimit;
            } else {
                this.mDx = -mShadowLimit;
            }
        } else {
            this.mDx = mDx;
        }
        setPadding();
        return this;
    }

    /**
     * 设置y轴阴影的偏移量
     *
     * @param mDy
     */
    public ShadowLayout setShadowOffsetY(float mDy) {
        if (Math.abs(mDy) > mShadowLimit) {
            if (mDy > 0) {
                this.mDy = mShadowLimit;
            } else {
                this.mDy = -mShadowLimit;
            }
        } else {
            this.mDy = mDy;
        }
        setPadding();

        return this;
    }

    /**
     * 获取当前的圆角值
     *
     * @return
     */
    public float getCornerRadius() {
        return mCornerRadius;
    }

    /**
     * 设置shadowLayout圆角
     *
     * @param mCornerRadius
     */

    public ShadowLayout setCornerRadius(int mCornerRadius) {
        this.mCornerRadius = mCornerRadius;
        if (getWidth() != 0 && getHeight() != 0) {
            setBackgroundCompat(getWidth(), getHeight());
        }
        return this;
    }

    /**
     * 获取阴影扩散区域值
     *
     * @return
     */
    public float getShadowLimit() {
        return mShadowLimit;
    }

    /**
     * 设置阴影扩散区域
     *
     * @param mShadowLimit
     */
    public ShadowLayout setShadowLimit(int mShadowLimit) {
        this.mShadowLimit = mShadowLimit;
        setPadding();
        return this;
    }

    /**
     * 设置阴影颜色值
     *
     * @param mShadowColor
     */

    public ShadowLayout setShadowColor(int mShadowColor) {
        this.mShadowColor = mShadowColor;
        if (getWidth() != 0 && getHeight() != 0) {
            setBackgroundCompat(getWidth(), getHeight());
        }
        return this;
    }


    /**
     * 单独设置4个圆角属性
     *
     * @param leftTop
     * @param rightTop
     * @param leftBottom
     * @param rightBottom
     */

    public ShadowLayout setSpecialCorner(int leftTop, int rightTop, int leftBottom, int rightBottom) {
        mCornerRadius_leftTop = leftTop;
        mCornerRadius_rightTop = rightTop;
        mCornerRadius_leftBottom = leftBottom;
        mCornerRadius_rightBottom = rightBottom;
        if (getWidth() != 0 && getHeight() != 0) {
            setBackgroundCompat(getWidth(), getHeight());
        }
        return this;
    }


    /**
     * 单独隐藏某边
     * setShadowHiddenTop：是否隐藏阴影的上边部分
     *
     * @param topShow
     */
    public ShadowLayout setShadowHiddenTop(boolean topShow) {
        this.topShow = !topShow;
        setPadding();
        return this;
    }

    public ShadowLayout setShadowHiddenBottom(boolean bottomShow) {
        this.bottomShow = !bottomShow;
        setPadding();
        return this;
    }


    public ShadowLayout setShadowHiddenRight(boolean rightShow) {
        this.rightShow = !rightShow;
        setPadding();
        return this;
    }


    public ShadowLayout setShadowHiddenLeft(boolean leftShow) {
        this.leftShow = !leftShow;
        setPadding();
        return this;
    }
}