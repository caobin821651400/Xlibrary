package com.cb.xlibrary.loadmore;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cb.xlibrary.R;
import com.cb.xlibrary.loadmore.api.RefreshFooter;
import com.cb.xlibrary.loadmore.api.RefreshKernel;
import com.cb.xlibrary.loadmore.api.RefreshLayout;
import com.cb.xlibrary.loadmore.api.RefreshState;
import com.cb.xlibrary.loadmore.drawable.PathsDrawable;
import com.cb.xlibrary.loadmore.drawable.ProgressDrawable;
import com.cb.xlibrary.utils.XDensityUtils;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * 描述：上拉加载更多
 * 作者：曹斌
 * date:2018/3/5 10:13
 */
public class LoadMoreFooter extends RelativeLayout implements RefreshFooter {

    public static String REFRESH_FOOTER_PULLUP = "上拉加载更多";
    public static String REFRESH_FOOTER_RELEASE = "释放立即加载";
    public static String REFRESH_FOOTER_LOADING = "正在加载...";
    public static String REFRESH_FOOTER_REFRESHING = "正在刷新...";
    public static String REFRESH_FOOTER_FINISH = "加载完成";
    public static String REFRESH_FOOTER_FAILED = "加载失败";
    public static String REFRESH_FOOTER_ALLLOADED = "全部加载完成";

    protected TextView mTitleText;
    protected ImageView mArrowView;
    protected ImageView mProgressView;
    protected PathsDrawable mArrowDrawable;
    protected ProgressDrawable mProgressDrawable;
    protected SpinnerStyle mSpinnerStyle = SpinnerStyle.FixedBehind;
    protected RefreshKernel mRefreshKernel;
    protected int mFinishDuration = 500;
    protected int mBackgroundColor = 0;
    protected boolean mLoadmoreFinished = false;
    protected int mPaddingTop = 20;
    protected int mPaddingBottom = 20;
    private Context mContext;


    public LoadMoreFooter(Context context) {
        this(context, null);
    }

    public LoadMoreFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {

        mTitleText = new TextView(context);
        mTitleText.setId(android.R.id.widget_frame);
        mTitleText.setTextColor(0xff666666);
        mTitleText.setText(REFRESH_FOOTER_PULLUP);

        LayoutParams lpBottomText = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpBottomText.addRule(CENTER_IN_PARENT);
        addView(mTitleText, lpBottomText);

        LayoutParams lpArrow = new LayoutParams(XDensityUtils.dp2px(context, 20), XDensityUtils.dp2px(context, 20));
        lpArrow.addRule(CENTER_VERTICAL);
        lpArrow.addRule(LEFT_OF, android.R.id.widget_frame);
        mArrowView = new ImageView(context);
        addView(mArrowView, lpArrow);

        LayoutParams lpProgress = new LayoutParams((ViewGroup.LayoutParams) lpArrow);
        lpProgress.addRule(CENTER_VERTICAL);
        lpProgress.addRule(LEFT_OF, android.R.id.widget_frame);
        mProgressView = new ImageView(context);
        mProgressView.animate().setInterpolator(new LinearInterpolator());
        addView(mProgressView, lpProgress);

        if (!isInEditMode()) {
            mProgressView.setVisibility(GONE);
        } else {
            mArrowView.setVisibility(GONE);
        }

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LoadMoreFooterView);

        lpProgress.rightMargin = ta.getDimensionPixelSize(R.styleable.LoadMoreFooterView_srlDrawableMarginRight, XDensityUtils.dp2px(context, 20));
        lpArrow.rightMargin = lpProgress.rightMargin;

        lpArrow.width = ta.getLayoutDimension(R.styleable.LoadMoreFooterView_srlDrawableArrowSize, lpArrow.width);
        lpArrow.height = ta.getLayoutDimension(R.styleable.LoadMoreFooterView_srlDrawableArrowSize, lpArrow.height);
        lpProgress.width = ta.getLayoutDimension(R.styleable.LoadMoreFooterView_srlDrawableProgressSize, lpProgress.width);
        lpProgress.height = ta.getLayoutDimension(R.styleable.LoadMoreFooterView_srlDrawableProgressSize, lpProgress.height);

        lpArrow.width = ta.getLayoutDimension(R.styleable.LoadMoreFooterView_srlDrawableSize, lpArrow.width);
        lpArrow.height = ta.getLayoutDimension(R.styleable.LoadMoreFooterView_srlDrawableSize, lpArrow.height);
        lpProgress.width = ta.getLayoutDimension(R.styleable.LoadMoreFooterView_srlDrawableSize, lpProgress.width);
        lpProgress.height = ta.getLayoutDimension(R.styleable.LoadMoreFooterView_srlDrawableSize, lpProgress.height);

        mFinishDuration = ta.getInt(R.styleable.LoadMoreFooterView_srlFinishDuration, mFinishDuration);

        if (ta.hasValue(R.styleable.LoadMoreFooterView_srlDrawableArrow)) {
            mArrowView.setImageDrawable(ta.getDrawable(R.styleable.LoadMoreFooterView_srlDrawableArrow));
        } else {
            mArrowDrawable = new PathsDrawable();
            mArrowDrawable.parserColors(0xff666666);
            mArrowDrawable.parserPaths("M20,12l-1.41,-1.41L13,16.17V4h-2v12.17l-5.58,-5.59L4,12l8,8 8,-8z");
            mArrowView.setImageDrawable(mArrowDrawable);
        }

        if (ta.hasValue(R.styleable.LoadMoreFooterView_srlDrawableProgress)) {
            mProgressView.setImageDrawable(ta.getDrawable(R.styleable.LoadMoreFooterView_srlDrawableProgress));
        } else {
            mProgressDrawable = new ProgressDrawable();
            mProgressDrawable.setColor(0xff666666);
            mProgressView.setImageDrawable(mProgressDrawable);
        }

        if (ta.hasValue(R.styleable.LoadMoreFooterView_srlTextSizeTitle)) {
            mTitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, ta.getDimensionPixelSize(R.styleable.LoadMoreFooterView_srlTextSizeTitle, XDensityUtils.dp2px(context, 16)));
        } else {
            mTitleText.setTextSize(16);
        }

        if (ta.hasValue(R.styleable.LoadMoreFooterView_srlPrimaryColor)) {
            setPrimaryColor(ta.getColor(R.styleable.LoadMoreFooterView_srlPrimaryColor, 0));
        }
        if (ta.hasValue(R.styleable.LoadMoreFooterView_srlAccentColor)) {
            setAccentColor(ta.getColor(R.styleable.LoadMoreFooterView_srlAccentColor, 0));
        }

        ta.recycle();

        if (getPaddingTop() == 0) {
            if (getPaddingBottom() == 0) {
                setPadding(getPaddingLeft(), mPaddingTop = XDensityUtils.dp2px(context, 20), getPaddingRight(), mPaddingBottom = XDensityUtils.dp2px(context, 20));
            } else {
                setPadding(getPaddingLeft(), mPaddingTop = XDensityUtils.dp2px(context, 20), getPaddingRight(), mPaddingBottom = getPaddingBottom());
            }
        } else {
            if (getPaddingBottom() == 0) {
                setPadding(getPaddingLeft(), mPaddingTop = getPaddingTop(), getPaddingRight(), mPaddingBottom = XDensityUtils.dp2px(context, 20));
            } else {
                mPaddingTop = getPaddingTop();
                mPaddingBottom = getPaddingBottom();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
            setPadding(getPaddingLeft(), 0, getPaddingRight(), 0);
        } else {
            setPadding(getPaddingLeft(), mPaddingTop, getPaddingRight(), mPaddingBottom);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {
        mRefreshKernel = kernel;
        mRefreshKernel.requestDrawBackgoundForFooter(mBackgroundColor);
    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {
    }

    @Override
    public void onPullingUp(float percent, int offset, int footerHeight, int extendHeight) {

    }

    @Override
    public void onPullReleasing(float percent, int offset, int headHeight, int extendHeight) {

    }

    @Override
    public void onLoadmoreReleased(RefreshLayout layout, int footerHeight, int extendHeight) {
        if (!mLoadmoreFinished) {
            mProgressView.setVisibility(VISIBLE);
            if (mProgressDrawable != null) {
                mProgressDrawable.start();
            } else {
                mProgressView.animate().rotation(36000).setDuration(100000);
            }
        }
    }

    @Override
    public void onStartAnimator(RefreshLayout layout, int headHeight, int extendHeight) {

    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        if (!mLoadmoreFinished) {
            if (mProgressDrawable != null) {
                mProgressDrawable.stop();
            } else {
                mProgressView.animate().rotation(0).setDuration(300);
            }
            mProgressView.setVisibility(GONE);
            if (success) {
                mTitleText.setText(REFRESH_FOOTER_FINISH);
            } else {
                mTitleText.setText(REFRESH_FOOTER_FAILED);
            }
            return mFinishDuration;
        }
        return 0;
    }

    /**
     * LoadMoreFooter 在(SpinnerStyle.FixedBehind)时才有主题色
     */
    @Override
    @Deprecated
    public void setPrimaryColors(@ColorInt int... colors) {
        if (mSpinnerStyle == SpinnerStyle.FixedBehind) {
            if (colors.length > 0) {
                if (!(getBackground() instanceof BitmapDrawable)) {
                    setPrimaryColor(colors[0]);
                }
                if (colors.length > 1) {
                    setAccentColor(colors[1]);
                } else {
                    setAccentColor(colors[0] == 0xffffffff ? 0xff666666 : 0xffffffff);
                }
            }
        }
    }

    /**
     * 设置数据全部加载完成，将不能再次触发加载功能
     */
    @Override
    public boolean setLoadmoreFinished(boolean finished) {
        if (mLoadmoreFinished != finished) {
            mLoadmoreFinished = finished;
            if (finished) {
                mTitleText.setText(REFRESH_FOOTER_ALLLOADED);
                mArrowView.setVisibility(GONE);
            } else {
                mTitleText.setText(REFRESH_FOOTER_PULLUP);
                mArrowView.setVisibility(VISIBLE);
            }
            if (mProgressDrawable != null) {
                mProgressDrawable.stop();
            } else {
                mProgressView.animate().rotation(0).setDuration(300);
            }
            mProgressView.setVisibility(GONE);
        }
        return true;
    }

    @NonNull
    public View getView() {
        return this;
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return mSpinnerStyle;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        if (!mLoadmoreFinished) {
            switch (newState) {
                case None:
                    mArrowView.setVisibility(VISIBLE);
                case PullToUpLoad:
                    mTitleText.setText(REFRESH_FOOTER_PULLUP);
                    mArrowView.animate().rotation(180);
                    break;
                case Loading:
                case LoadReleased:
                    mArrowView.setVisibility(GONE);
                    mTitleText.setText(REFRESH_FOOTER_LOADING);
                    break;
                case ReleaseToLoad:
                    mTitleText.setText(REFRESH_FOOTER_RELEASE);
                    mArrowView.animate().rotation(0);
                    break;
                case Refreshing:
                    mTitleText.setText(REFRESH_FOOTER_REFRESHING);
                    mProgressView.setVisibility(GONE);
                    mArrowView.setVisibility(GONE);
                    break;
            }
        }
    }

    public LoadMoreFooter setProgressBitmap(Bitmap bitmap) {
        mProgressDrawable = null;
        mProgressView.setImageBitmap(bitmap);
        return this;
    }

    public LoadMoreFooter setProgressDrawable(Drawable drawable) {
        mProgressDrawable = null;
        mProgressView.setImageDrawable(drawable);
        return this;
    }

    public LoadMoreFooter setProgressResource(@DrawableRes int resId) {
        mProgressDrawable = null;
        mProgressView.setImageResource(resId);
        return this;
    }

    public LoadMoreFooter setArrowBitmap(Bitmap bitmap) {
        mArrowDrawable = null;
        mArrowView.setImageBitmap(bitmap);
        return this;
    }

    public LoadMoreFooter setArrowDrawable(Drawable drawable) {
        mArrowDrawable = null;
        mArrowView.setImageDrawable(drawable);
        return this;
    }

    public LoadMoreFooter setArrowResource(@DrawableRes int resId) {
        mArrowDrawable = null;
        mArrowView.setImageResource(resId);
        return this;
    }

    public LoadMoreFooter setSpinnerStyle(SpinnerStyle style) {
        this.mSpinnerStyle = style;
        return this;
    }

    public LoadMoreFooter setAccentColor(@ColorInt int accentColor) {
        mTitleText.setTextColor(accentColor);
        if (mProgressDrawable != null) {
            mProgressDrawable.setColor(accentColor);
        }
        if (mArrowDrawable != null) {
            mArrowDrawable.parserColors(accentColor);
        }
        return this;
    }

    public LoadMoreFooter setPrimaryColor(@ColorInt int primaryColor) {
        setBackgroundColor(mBackgroundColor = primaryColor);
        if (mRefreshKernel != null) {
            mRefreshKernel.requestDrawBackgoundForFooter(mBackgroundColor);
        }
        return this;
    }

    public LoadMoreFooter setPrimaryColorId(@ColorRes int colorId) {
        setPrimaryColor(ContextCompat.getColor(getContext(), colorId));
        return this;
    }

    public LoadMoreFooter setAccentColorId(@ColorRes int colorId) {
        setAccentColor(ContextCompat.getColor(getContext(), colorId));
        return this;
    }

    public LoadMoreFooter setFinishDuration(int delay) {
        mFinishDuration = delay;
        return this;
    }

    public LoadMoreFooter setTextSizeTitle(float size) {
        mTitleText.setTextSize(size);
        if (mRefreshKernel != null) {
            mRefreshKernel.requestRemeasureHeightForFooter();
        }
        return this;
    }

    public LoadMoreFooter setTextSizeTitle(int unit, float size) {
        mTitleText.setTextSize(unit, size);
        if (mRefreshKernel != null) {
            mRefreshKernel.requestRemeasureHeightForFooter();
        }
        return this;
    }

    public LoadMoreFooter setDrawableMarginRight(float dp) {
        return setDrawableMarginRightPx(XDensityUtils.dp2px(mContext, dp));
    }

    public LoadMoreFooter setDrawableMarginRightPx(int px) {
        MarginLayoutParams lpArrow = (MarginLayoutParams) mArrowView.getLayoutParams();
        MarginLayoutParams lpProgress = (MarginLayoutParams) mProgressView.getLayoutParams();
        lpArrow.rightMargin = lpProgress.rightMargin = px;
        mArrowView.setLayoutParams(lpArrow);
        mProgressView.setLayoutParams(lpProgress);
        return this;
    }

    public LoadMoreFooter setDrawableSize(float dp) {
        return setDrawableSizePx(XDensityUtils.dp2px(mContext, dp));
    }

    public LoadMoreFooter setDrawableSizePx(int px) {
        ViewGroup.LayoutParams lpArrow = mArrowView.getLayoutParams();
        ViewGroup.LayoutParams lpProgress = mProgressView.getLayoutParams();
        lpArrow.width = lpProgress.width = px;
        lpArrow.height = lpProgress.height = px;
        mArrowView.setLayoutParams(lpArrow);
        mProgressView.setLayoutParams(lpProgress);
        return this;
    }

    public LoadMoreFooter setDrawableArrowSize(float dp) {
        return setDrawableArrowSizePx(XDensityUtils.dp2px(mContext, dp));
    }

    public LoadMoreFooter setDrawableArrowSizePx(int px) {
        ViewGroup.LayoutParams lpArrow = mArrowView.getLayoutParams();
        lpArrow.width = px;
        lpArrow.height = px;
        mArrowView.setLayoutParams(lpArrow);
        return this;
    }

    public LoadMoreFooter setDrawableProgressSize(float dp) {
        return setDrawableProgressSizePx(XDensityUtils.dp2px(mContext, dp));
    }

    public LoadMoreFooter setDrawableProgressSizePx(int px) {
        ViewGroup.LayoutParams lpProgress = mProgressView.getLayoutParams();
        lpProgress.width = px;
        lpProgress.height = px;
        mProgressView.setLayoutParams(lpProgress);
        return this;
    }

    public TextView getTitleText() {
        return mTitleText;
    }

    public ImageView getProgressView() {
        return mProgressView;
    }

    public ImageView getArrowView() {
        return mArrowView;
    }
}
