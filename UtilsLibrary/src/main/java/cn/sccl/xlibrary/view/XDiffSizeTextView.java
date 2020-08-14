package cn.sccl.xlibrary.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.cb.xlibrary.R;

import cn.sccl.xlibrary.utils.XLogUtils;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2020/7/28 18:07
 * @Desc :大小不一样的TextView
 * ====================================================
 */
public class XDiffSizeTextView extends View {

    private String mLeftTxt, mRightTxt;
    private int mLeftTxtColor, mRightTxtColor;
    private float mLeftTxtSize, mRightTxtSize;
    private Rect mLeftTxtRect, mRightTxtRect;
    private Paint mLeftTxtPaint, mRightTxtPaint;
    private int mLeftTxtWidth, mRightTxtWidth;
    private int mLeftTxtHeight, mRightTxtHeight;

    private Drawable drawable;
    private Bitmap mBitmap;
    private int bitmapPadding = 10;
    private int txtMargin = 3;//两个文字间距

    public XDiffSizeTextView(Context context) {
        this(context, null);
    }

    public XDiffSizeTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XDiffSizeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.XDiffSizeTextView);
        mLeftTxt = typedArray.getString(R.styleable.XDiffSizeTextView_xds_left_txt);
        mRightTxt = typedArray.getString(R.styleable.XDiffSizeTextView_xds_right_txt);
        mLeftTxtSize = typedArray.getDimension(R.styleable.XDiffSizeTextView_xds_left_txt_size, sp2px(16));
        mRightTxtSize = typedArray.getDimension(R.styleable.XDiffSizeTextView_xds_right_txt_size, sp2px(10));

        mLeftTxtColor = typedArray.getColor(R.styleable.XDiffSizeTextView_xds_left_txt_color, 0x000000);
        mRightTxtColor = typedArray.getColor(R.styleable.XDiffSizeTextView_xds_right_txt_color, 0x000000);
        drawable = typedArray.getDrawable(R.styleable.XDiffSizeTextView_xds_icon);

        typedArray.recycle();
        init();
    }

    private void init() {
        mLeftTxtRect = new Rect();
        mRightTxtRect = new Rect();

        if (!TextUtils.isEmpty(mLeftTxt)) {
            mLeftTxtPaint = new Paint();
            mLeftTxtPaint.setStyle(Paint.Style.FILL);
            mLeftTxtPaint.setAntiAlias(true);
        }

        if (!TextUtils.isEmpty(mRightTxt)) {
            mRightTxtPaint = new Paint();
            mRightTxtPaint.setStyle(Paint.Style.FILL);
            mRightTxtPaint.setAntiAlias(true);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        XLogUtils.d("onMeasure");

        if (drawable != null) {
            mBitmap = drawableToBitmap(drawable);
        }

        if (!TextUtils.isEmpty(mLeftTxt)) {
            mLeftTxtPaint.setTextSize(mLeftTxtSize);
            mLeftTxtPaint.getTextBounds(mLeftTxt, 0, mLeftTxt.length(), mLeftTxtRect);
            mLeftTxtWidth = (int) mLeftTxtPaint.measureText(mLeftTxt);
            mLeftTxtHeight = (int) (mLeftTxtPaint.descent() - mLeftTxtPaint.ascent());
        }

        if (!TextUtils.isEmpty(mRightTxt)) {
            mRightTxtPaint.setTextSize(mRightTxtSize);
            mRightTxtPaint.getTextBounds(mRightTxt, 0, mRightTxt.length(), mRightTxtRect);
            mRightTxtWidth = (int) mRightTxtPaint.measureText(mRightTxt);
            mRightTxtHeight = (int) (mRightTxtPaint.descent() - mRightTxtPaint.ascent());
        }

        setMeasuredDimension(mBitmap == null ? mLeftTxtWidth + mRightTxtWidth :
                        mLeftTxtWidth + mRightTxtWidth + mBitmap.getWidth() + bitmapPadding + txtMargin
                , Math.max(mLeftTxtHeight, mRightTxtHeight));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        XLogUtils.d("onDraw");

        //画左边bitmap  -3往上稍微偏移一下
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, mLeftTxtHeight / 2 - 3, mLeftTxtPaint);
        }

        //画左边的文字 -5是不想去计算baseline的高度
        if (!TextUtils.isEmpty(mLeftTxt)) {
            mLeftTxtPaint.setColor(mLeftTxtColor);
            canvas.drawText(mLeftTxt, mBitmap == null ? 0 : mBitmap.getWidth() + bitmapPadding, mLeftTxtHeight - 5, mLeftTxtPaint);
        }
        //画右边的文字 -5是不想去计算baseline的高度
        if (!TextUtils.isEmpty(mRightTxt)) {
            mRightTxtPaint.setColor(mRightTxtColor);
            canvas.drawText(mRightTxt, mBitmap == null ? (mLeftTxtWidth + txtMargin)
                            : mBitmap.getWidth() + bitmapPadding + txtMargin + mLeftTxtWidth
                    , mLeftTxtHeight - 5, mRightTxtPaint);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * @return
     */
    public String getLeftTxt() {
        return mLeftTxt;
    }

    /**
     * @param mLeftTxt
     */
    public void setLeftTxt(String mLeftTxt) {
        this.mLeftTxt = mLeftTxt;
        refresh(true);
    }

    /**
     * @return
     */
    public String getRightTxt() {
        return mRightTxt;
    }

    /**
     * @param mRightTxt
     */
    public void setRightTxt(String mRightTxt) {
        this.mRightTxt = mRightTxt;
        refresh(true);
    }

    /**
     * @return
     */
    public int getLeftTxtColor() {
        return mLeftTxtColor;
    }

    /**
     * @param mLeftTxtColor
     */
    public void setLeftTxtColor(@ColorInt int mLeftTxtColor) {
        this.mLeftTxtColor = mLeftTxtColor;
        refresh();
    }

    /**
     * @return
     */
    public int getRightTxtColor() {
        return mRightTxtColor;
    }

    /**
     * @param mRightTxtColor
     */
    public void setRightTxtColor(@ColorInt int mRightTxtColor) {
        this.mRightTxtColor = mRightTxtColor;
        refresh();
    }

    /**
     * @return
     */
    public float getLeftTxtSize() {
        return mLeftTxtSize;
    }

    /**
     * @param mLeftTxtSize
     */
    public void setLeftTxtSize(float mLeftTxtSize) {
        this.mLeftTxtSize = sp2px((int) mLeftTxtSize);
        refresh(true);
    }

    /**
     * @return
     */
    public float getRightTxtSize() {
        return mRightTxtSize;
    }

    /**
     * @param mRightTxtSize
     */
    public void setRightTxtSize(float mRightTxtSize) {
        this.mRightTxtSize = sp2px((int) mRightTxtSize);
        refresh(true);
    }

    /**
     * @return
     */
    public Drawable getDrawable() {
        return drawable;
    }

    /**
     * @param drawable
     */
    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
        if (mBitmap != null) {
            mBitmap.recycle();
            mBitmap = null;
        }
        refresh(true);
    }

    /**
     * 重新绘制 适用于颜色变化大小未变
     */
    private void refresh() {
        refresh(false);
    }

    /**
     * 重新绘制
     *
     * @param isSizeChang 大小是否变化，需要重新计算宽高
     */
    private void refresh(boolean isSizeChang) {
        if (isSizeChang) {
            requestLayout();
            invalidate();
        } else {
            invalidate();
        }
    }

    /**
     * @param dp
     * @return
     */
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    /**
     * @param sp
     * @return
     */
    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                Resources.getSystem().getDisplayMetrics());
    }

    /**
     * Drawable 转 Bitmap
     *
     * @param drawable
     * @return
     */
    private Bitmap drawableToBitmap(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }
}
