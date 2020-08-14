package cn.sccl.xlibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.cb.xlibrary.R;

import cn.sccl.xlibrary.utils.XDensityUtils;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2019/10/12 14:34
 * @Desc :设置左右显示文字布局
 * ====================================================
 */
public class XSettingView extends LinearLayout {

    private XAsteriskTextView tvHeader;
    private TextView tvEnd;
    //第一个TV
    private boolean isMustInput;
    private String mTileTxt;
    private float mTileTxtSize;
    private int mTileTxtColor;
    private int headerWidth;
    //第二个TV
    private String mEndTxt;
    private String mHintTxt;
    private float mEndTxtSize;
    private int mEndTxtColor;
    private boolean mEndTxtShowArrow;//显示向右箭头
    private Drawable mRightDrawable;
    private int mDrawablePadding;//单位DP
    private boolean isShowHintTxt;
    private int mEndTxtGravity;

    public XSettingView(Context context) {
        this(context, null);
    }

    public XSettingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XSettingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //读取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.XSettingView);

        isMustInput = typedArray.getBoolean(R.styleable.XSettingView_item_is_must_input, false);

        mTileTxt = typedArray.getString(R.styleable.XSettingView_item_title_txt);
        mEndTxt = typedArray.getString(R.styleable.XSettingView_item_end_txt);
        mHintTxt = typedArray.getString(R.styleable.XSettingView_item_hint_txt);
        if (TextUtils.isEmpty(mHintTxt)) mHintTxt = "请选择" + mTileTxt;

        mTileTxtSize = typedArray.getDimension(R.styleable.XSettingView_item_title_txt_size, XDensityUtils.sp2px(context, 14));
        mEndTxtSize = typedArray.getDimension(R.styleable.XSettingView_item_end_txt_size, XDensityUtils.sp2px(context, 14));
        headerWidth = typedArray.getDimensionPixelSize(R.styleable.XSettingView_item_header_width, XDensityUtils.dp2px(context, 90));

        mTileTxtColor = typedArray.getColor(R.styleable.XSettingView_item_title_txt_color, 0xff2A2A2A);
        mEndTxtColor = typedArray.getColor(R.styleable.XSettingView_item_end_txt_color, 0xff808080);

        mEndTxtShowArrow = typedArray.getBoolean(R.styleable.XSettingView_item_end_show_arrow, false);
        isShowHintTxt = typedArray.getBoolean(R.styleable.XSettingView_item_is_show_hint, true);
        if (mEndTxtShowArrow) {
            mRightDrawable = typedArray.getDrawable(R.styleable.XSettingView_item_end_txt_drawable_right);
            mDrawablePadding = typedArray.getDimensionPixelSize(R.styleable.XSettingView_item_end_txt_padding, 0);
        }
        mEndTxtGravity = typedArray.getInt(R.styleable.XSettingView_item_end_txt_gravity, Gravity.LEFT);
        typedArray.recycle();
        init(context, attrs);
    }


    private void init(Context context, @Nullable AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.layout_common_setting_view_cblibrary, this);
        tvHeader = findViewById(R.id.tv_header);
        tvEnd = findViewById(R.id.tv_end);

        tvHeader.setIsMust(isMustInput);
        tvHeader.setmText(mTileTxt);
        tvHeader.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTileTxtSize);
        tvHeader.setTextColor(mTileTxtColor);
        tvHeader.setLayoutParams(new LinearLayout.LayoutParams(headerWidth, LayoutParams.MATCH_PARENT));

        if (TextUtils.isEmpty(mEndTxt)) {
            if (isShowHintTxt)
                tvEnd.setHint(mHintTxt);
        } else {
            tvEnd.setText(mEndTxt);
        }
        tvEnd.setTextSize(TypedValue.COMPLEX_UNIT_PX, mEndTxtSize);
        tvEnd.setTextColor(mEndTxtColor);
        tvEnd.setGravity(Gravity.CENTER_VERTICAL | mEndTxtGravity);
        if (mEndTxtShowArrow) {
            tvEnd.setCompoundDrawablesWithIntrinsicBounds(null, null, mRightDrawable, null);
            tvEnd.setCompoundDrawablePadding(mDrawablePadding);
        }
    }


    public XAsteriskTextView getTvHeader() {
        return tvHeader;
    }

    /**
     * 防止一些超长字 不够显示的
     *
     * @param dp
     */
    public void setHeaderTvWidth(int dp) {
        tvHeader.setLayoutParams(new LinearLayout.LayoutParams(XDensityUtils.dp2px(getContext(), dp), LayoutParams.MATCH_PARENT));
    }

    //
//    public void setTvHeader(XAsteriskTextView tvHeader) {
//        this.tvHeader = tvHeader;
//    }
//
    public TextView getTvEnd() {
        return tvEnd;
    }
//
//    public void setTvEnd(TextView tvEnd) {
//        this.tvEnd = tvEnd;
//    }

    public boolean isMustInput() {
        return isMustInput;
    }

    public void setustInput(boolean mustInput) {
        isMustInput = mustInput;
        tvHeader.setIsMust(isMustInput);
    }

    public String getTileTxt() {
        return mTileTxt;
    }

    public void setTileTxt(String mTileTxt) {
        this.mTileTxt = mTileTxt;
        tvHeader.setText(mTileTxt);
    }

    public int getEndTxtGravity() {
        return mEndTxtGravity;
    }

    public void setEndTxtGravity(int mEndTxtGravity) {
        this.mEndTxtGravity = mEndTxtGravity;
        tvEnd.setGravity(Gravity.CENTER_VERTICAL | mEndTxtGravity);
    }

    public float getTileTxtSize() {
        return mTileTxtSize;
    }

    public void setTileTxtSize(float mTileTxtSize) {
        this.mTileTxtSize = mTileTxtSize;
        tvHeader.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTileTxtSize);
    }

    public void setHintTxt(String txt) {
        this.mHintTxt = txt;
        tvEnd.setHint(mHintTxt);
    }

    public int getTileTxtColor() {
        return mTileTxtColor;
    }

    public void setTileTxtColor(@ColorInt int mTileTxtColor) {
        this.mTileTxtColor = mTileTxtColor;
        tvHeader.setTextColor(mTileTxtColor);
    }

    public String getEndTxt() {
        return mEndTxt;
    }

    public void setEndTxt(String mEndTxt) {
        this.mEndTxt = mEndTxt;
        tvEnd.setText(mEndTxt);
    }

    public float getEndTxtSize() {
        return mEndTxtSize;
    }

    public void setEndTxtSize(float mEndTxtSize) {
        this.mEndTxtSize = mEndTxtSize;
        tvEnd.setTextSize(TypedValue.COMPLEX_UNIT_PX, mEndTxtSize);
    }

    public int getEndTxtColor() {
        return mEndTxtColor;
    }

    public void setEndTxtColor(@ColorInt int mEndTxtColor) {
        this.mEndTxtColor = mEndTxtColor;
        tvEnd.setTextColor(mEndTxtColor);
    }

    public boolean ismEndTxtShowArrow() {
        return mEndTxtShowArrow;
    }

    public void setEndTxtShowArrow(boolean mEndTxtShowArrow) {
        this.mEndTxtShowArrow = mEndTxtShowArrow;
    }

    public Drawable getRightDrawable() {
        return mRightDrawable;
    }

    public void setRightDrawable(Drawable mRightDrawable) {
        this.mRightDrawable = mRightDrawable;
        if (mEndTxtShowArrow) {
            tvEnd.setCompoundDrawablesWithIntrinsicBounds(null, null, this.mRightDrawable, null);
        }
    }

    public int getDrawablePadding() {
        return mDrawablePadding;
    }

    public void setDrawablePadding(int mDrawablePadding) {
        this.mDrawablePadding = mDrawablePadding;
        if (mEndTxtShowArrow) {
            tvEnd.setCompoundDrawablePadding(mDrawablePadding);
        }
    }
}
