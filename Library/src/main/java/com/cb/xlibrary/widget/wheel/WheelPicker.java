package com.cb.xlibrary.widget.wheel;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import com.cb.xlibrary.R;

import java.text.Format;
import java.util.List;

/**
 * 描述：轮子选择器
 * 作者：曹斌
 * date:2018/3/16 14:22
 */
public class WheelPicker<T> extends View {
    //数据集
    private List<T> mDataList;
    private Format mFormat;
    //字体颜色
    @ColorInt
    private int mTextColor;
    //字体大小
    private int mTextSize;
    //字体是否渐变，越靠近边缘字体月迷糊
    private boolean mIsTextGradual;
    //选中的text的颜色
    @ColorInt
    private int mSelecedTextColot;
    //选中text大小
    private int mSelectedTextSize;
    //选中数字后面的文字（例如 12 年）中的年
    private String mIndicationAfterText;
    //指示器字颜色
    @ColorInt
    private int mIndicationTextColor;
    //指示器字体大小
    private int mIndicationTextSize;
    //画笔
    private Paint mPaint;
    //最大的一个文本的宽高度
    private int mTextMaxWidth, mTextMaxHeight;
    //输入的一段文字，可以用来测量 mTextMaxWidth
    private String mItemMaximumWidthText;
    //中心items(也就是选中的item)上下item的数量
    private int mHalfVisibleItemCount;
    //两个item的宽高间隔
    private int mItemWidthSpace, mItemHeightSpace;
    //item的高度
    private int mItenHeight;
    //当前item的位置
    private int currentItemPosition;
    //选中的item是否方法
    private boolean mIsZoomSelectedItem;
    //是否显示幕布，中央Item会遮盖一个颜色颜色
    private boolean mIsShowCurtain;
    //幕布颜色
    @ColorInt
    private int mCurtainColor;
    //是否显示幕布的边框
    private boolean mIsShowCurtainBorder;
    //幕布边框的颜色
    @ColorInt
    private int mCurtainBorderColor;
    //整个控件的可绘制面积
    private Rect mDrawnRect;
    //中心被选中的Item的坐标矩形
    private Rect mSelectedItemRect;
    //第一个Item的绘制Text的坐标
    private int mFirstItemDrawX, mFirstItemDrawY;
    //中心的Item绘制text的Y轴坐标
    private int mCenterItemDrawnY;
    //
    private Scroller mScroller;
    //滑动距离
    private int mTouchSlop;
    //该标记的作用是，令mTouchSlop仅在一个滑动过程中生效一次
    private boolean mTouchSlopFlag;
    //
    private VelocityTracker mTracker;
    //
    private int mTouchDownY;
    //Y轴Scroll滚动的位移
    private int mScrollOffsetY;
    //最后手指Down事件的Y轴坐标，用于计算拖动距离
    private int mLastDownY;
    //是否循环读取
    private boolean mIsCyclic = true;
    //最大可以急速滑动的距离
    private int mMaxFlingY, mMinFlingY;
    //滚轮滑动时的最小/最大速度
    private int mMinimumVelocity = 50, mMaximumVelocity = 12000;
    //是否是手动停止滚动
    private boolean mIsAbortScroller;
    //
    private LinearGradient mLinearGradient;
    //
    private Handler mHandler = new Handler();
    //
    private OnWheelChangeListener<T> mOnWheelChangeListener;

    public interface OnWheelChangeListener<T> {
        void onWheelSelected(T item, int position);
    }

    //滑动
    private Runnable mScrollerRunnable = new Runnable() {
        @Override
        public void run() {
            if (mScroller.computeScrollOffset()) {
                mScrollOffsetY = mScroller.getCurrY();
                postInvalidate();
                mHandler.postDelayed(this, 16);
            }
            if (mScroller.isFinished()) {
                if (mOnWheelChangeListener == null || mItenHeight == 0) {
                    return;
                }
                int position = -mScrollOffsetY / mItenHeight;
                position = fixItemPosition(position);

                if (currentItemPosition != position) {
                    position = currentItemPosition;
                    mOnWheelChangeListener.onWheelSelected(mDataList.get(position), position);
                }
            }
        }
    };


    public WheelPicker(Context context) {
        this(context, null);
    }

    public WheelPicker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WheelPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
//        initPaint();
//        mLinearGradient = new LinearGradient(mTextColor, mSelectedItemTextColor);
        mDrawnRect = new Rect();
        mSelectedItemRect = new Rect();
        mScroller = new Scroller(context);

        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
    }

    private void initAttrs(Context context, @Nullable AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WheelPicker);
        mTextSize = a.getDimensionPixelSize(R.styleable.WheelPicker_itemTextSize,
                getResources().getDimensionPixelSize(R.dimen.WheelItemTextSize));
        mTextColor = a.getColor(R.styleable.WheelPicker_itemTextColor,
                Color.BLACK);
        mIsTextGradual = a.getBoolean(R.styleable.WheelPicker_textGradual, true);
        mIsCyclic = a.getBoolean(R.styleable.WheelPicker_wheelCyclic, false);
        mHalfVisibleItemCount = a.getInteger(R.styleable.WheelPicker_halfVisibleItemCount, 2);
        mItemMaximumWidthText = a.getString(R.styleable.WheelPicker_itemMaximumWidthText);
        mSelecedTextColot = a.getColor(R.styleable.WheelPicker_selectedTextColor, Color.parseColor("#33aaff"));
        mSelectedTextSize = a.getDimensionPixelSize(R.styleable.WheelPicker_selectedTextSize,
                getResources().getDimensionPixelSize(R.dimen.WheelSelectedItemTextSize));
        currentItemPosition= a.getInteger(R.styleable.WheelPicker_currentItemPosition, 0);
        mItemWidthSpace = a.getDimensionPixelSize(R.styleable.WheelPicker_itemWidthSpace,
                getResources().getDimensionPixelOffset(R.dimen.WheelItemWidthSpace));
        mItemHeightSpace = a.getDimensionPixelSize(R.styleable.WheelPicker_itemHeightSpace,
                getResources().getDimensionPixelOffset(R.dimen.WheelItemHeightSpace));
        mIsZoomSelectedItem = a.getBoolean(R.styleable.WheelPicker_zoomInSelectedItem, true);
        mIsShowCurtain = a.getBoolean(R.styleable.WheelPicker_wheelCurtain, true);
        mCurtainColor = a.getColor(R.styleable.WheelPicker_wheelCurtainColor,
                Color.parseColor("#303d3d3d"));
        mIsShowCurtainBorder = a.getBoolean(R.styleable.WheelPicker_wheelCurtainBorder, true);
        mCurtainBorderColor = a.getColor(R.styleable.WheelPicker_wheelCurtainBorderColor, Color.BLACK);
        mIndicationAfterText = a.getString(R.styleable.WheelPicker_indicatorText);
        mIndicationTextSize = a.getDimensionPixelSize(R.styleable.WheelPicker_indicatorTextSize, mTextSize);
        a.recycle();
    }

    /**
     * 修正坐标值，让其回到dateList的范围内
     *
     * @param position 修正前的值
     * @return 修正后的值
     */
    private int fixItemPosition(int position) {
        if (position < 0) {
            //将数据集限定在0 ~ mDataList.size()-1之间
            position = mDataList.size() + (position % mDataList.size());
        }
        if (position > mDataList.size()) {
            //将数据集限定在0 ~ mDataList.size()-1之间
            position = position % mDataList.size();
        }
        return position;
    }
}
