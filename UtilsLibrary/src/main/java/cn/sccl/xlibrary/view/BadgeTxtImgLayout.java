package cn.sccl.xlibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.cb.xlibrary.R;

import cn.sccl.xlibrary.utils.XDensityUtils;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2019/10/11 11:50
 * @Desc :上面图片下面文字的布局(可以设置角标)
 * ====================================================
 */
public class BadgeTxtImgLayout extends ConstraintLayout {

    private View rootView;
    private String mTitle = "";//标题
    private int mCount = -1;//未读数量
    private Drawable mIconDrawable;//圆背景
    private Drawable mCountDrawable;//图片
    private TextView tvTitle;
    private TextView tvCount;
    private ImageView ivImage;
    private int mTitleTxtColor;//标题字颜色
    private float mTitleTxtSize;//标题字大小
    private int mCountTxtColor;//角标字颜色
    private float mCountTitleTxtSize;//角标字大小
    private Context mContext;
    private Space mSpace;
    private float mSpacePadding;

    public BadgeTxtImgLayout(@NonNull Context context) {
        this(context, null);
    }

    public BadgeTxtImgLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BadgeTxtImgLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        this.mContext = context;
        rootView = LayoutInflater.from(context).inflate(R.layout.view_badge_txt_img_layout, this);
        tvTitle = rootView.findViewById(R.id.tv_title);
        tvCount = rootView.findViewById(R.id.tv_count);
        ivImage = rootView.findViewById(R.id.iv_icon);
        mSpace = rootView.findViewById(R.id.space);
        //读取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BadgeTxtImgLayout);
        mCount = typedArray.getInteger(R.styleable.BadgeTxtImgLayout_badge_count, 0);
        mTitle = typedArray.getString(R.styleable.BadgeTxtImgLayout_badge_title_txt);
        mIconDrawable = typedArray.getDrawable(R.styleable.BadgeTxtImgLayout_badge_icon);
        mCountDrawable = typedArray.getDrawable(R.styleable.BadgeTxtImgLayout_badge_count_bg);
        mTitleTxtColor = typedArray.getColor(R.styleable.BadgeTxtImgLayout_badge_title_txt_color, 0xff3f3f3f);
        mCountTxtColor = typedArray.getColor(R.styleable.BadgeTxtImgLayout_badge_count_txt_color, 0xffd50000);
        mTitleTxtSize = typedArray.getDimension(R.styleable.BadgeTxtImgLayout_badge_title_txt_size, XDensityUtils.sp2px(context, 11));
        mCountTitleTxtSize = typedArray.getDimension(R.styleable.BadgeTxtImgLayout_badge_count_txt_size, 8);
        mSpacePadding = typedArray.getDimension(R.styleable.BadgeTxtImgLayout_badge_count_padding, 0);
        typedArray.recycle();
        //角标
        tvCount.setTextSize(mCountTitleTxtSize);
        tvCount.setTextColor(mCountTxtColor);
        if (mCount == 0) {
            tvCount.setVisibility(View.GONE);
        } else {
            tvCount.setVisibility(View.VISIBLE);
            tvCount.setText(mCount + "");
        }
        if (mCountDrawable != null) {
            tvCount.setBackground(mCountDrawable);
        }
        //标题
        tvTitle.setText(mTitle);
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleTxtSize);
        tvTitle.setTextColor(mTitleTxtColor);
        //图片
        ivImage.setImageDrawable(mIconDrawable);
//        tvTitle.setCompoundDrawablePadding(mPadding);
//        tvTitle.setCompoundDrawablesWithIntrinsicBounds(null, mIconDrawable, null, null);

        //角标园的padding，对称的
        if (mSpacePadding != 0.0) {
            ConstraintLayout.LayoutParams params = (LayoutParams) mSpace.getLayoutParams();
            params.rightMargin = (int) mSpacePadding;
            params.topMargin = (int) mSpacePadding;
//            mSpace.setLayoutParams(params);
        }
    }

    /**
     * @return
     */
    public int getCount() {
        return mCount;
    }

    /**
     * @param mCount
     */
    public void setCount(int mCount) {
        this.mCount = mCount;
        if (mCount == 0) {
            tvCount.setVisibility(View.GONE);
        } else {
            tvCount.setVisibility(View.VISIBLE);
            tvCount.setText(mCount >= 100 ? "99+" : mCount + "");
        }
    }

    /**
     * @param id
     */
    public void setIconResource(@DrawableRes int id) {
        this.mIconDrawable = ContextCompat.getDrawable(mContext, id);
        ivImage.setImageDrawable(mIconDrawable);
    }

    /**
     * @return
     */
    public Drawable getIconDrawable() {
        return mIconDrawable;
    }

    /**
     * @param mIconDrawable
     */
    public void setIconDrawable(Drawable mIconDrawable) {
        this.mIconDrawable = mIconDrawable;
        ivImage.setImageDrawable(mIconDrawable);
    }

    /**
     * @return
     */
    public Drawable getCountDrawable() {
        return mCountDrawable;
    }

    /**
     * @param mCountDrawable
     */
    public void setCountDrawable(Drawable mCountDrawable) {
        this.mCountDrawable = mCountDrawable;
        tvCount.setBackground(mCountDrawable);
    }

    /**
     * @return
     */
    public TextView getTvTitle() {
        return tvTitle;
    }

    /**
     * @return
     */
    public TextView getTvCount() {
        return tvCount;
    }

    /**
     * @return
     */
    public int getTitleTxtColor() {
        return mTitleTxtColor;
    }

    /**
     * @param mTitleTxtColor
     */
    public void setTitleTxtColor(@ColorInt int mTitleTxtColor) {
        this.mTitleTxtColor = mTitleTxtColor;
        tvTitle.setTextColor(mTitleTxtColor);
    }

    /**
     * @return
     */
    public float getTitleTxtSize() {
        return mTitleTxtSize;
    }

    /**
     * @param mTitleTxtSize
     */
    public void setTitleTxtSize(float mTitleTxtSize) {
        this.mTitleTxtSize = mTitleTxtSize;
//        tvTitle.setTextSize(mTitleTxtSize);
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleTxtSize);
    }

    /**
     * @return
     */
    public int getCountTxtColor() {
        return mCountTxtColor;
    }

    /**
     * @param mCountTxtColor
     */
    public void setCountTxtColor(@ColorInt int mCountTxtColor) {
        this.mCountTxtColor = mCountTxtColor;
        if (tvCount.getVisibility() == VISIBLE) {
            tvCount.setTextColor(mCountTxtColor);
        }
    }

    /**
     * @return
     */
    public float getCountTitleTxtSize() {
        return mCountTitleTxtSize;
    }

    /**
     * @param mCountTitleTxtSize
     */
    public void setCountTitleTxtSize(float mCountTitleTxtSize) {
        this.mCountTitleTxtSize = mCountTitleTxtSize;
        if (tvCount.getVisibility() == VISIBLE) {
            tvCount.setTextSize(mCountTitleTxtSize);
        }
    }

    /**
     * @return
     */
    public String getTtitle() {
        return mTitle;
    }

    /**
     * @param mTitle
     */
    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
        tvTitle.setText(mTitle);
    }
}
