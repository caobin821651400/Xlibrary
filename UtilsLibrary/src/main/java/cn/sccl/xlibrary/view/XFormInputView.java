package cn.sccl.xlibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.cb.xlibrary.R;

import cn.sccl.xlibrary.utils.XDensityUtils;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2019/10/12 14:34
 * @Desc :办公APP，标题-输入的view   类似 (姓名：XXXXX)
 * ====================================================
 */
public class XFormInputView extends LinearLayout {

    private XAsteriskTextView tvHeader;
    private EditText mEditText;
    //第一个TV
    private boolean isMustInput;
    private String mTileTxt;
    private float mTileTxtSize;
    private int mTileTxtColor;
    private int headerWidth;
    //第二个EditText
    private String mInputTxt;
    private String mHintTxt;//默认的提示
    private float mInputTxtSize;
    private int mInputTxtColor;
    private int mHintTxtColor;
    private int mEndTxtGravity;
    private int maxLength = 0;

    //传数字即可，具体的请看 android.text.InputType
    //几种常见的 2=number   128密码
    private int mInputType;

    public XFormInputView(Context context) {
        this(context, null);
    }

    public XFormInputView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XFormInputView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //读取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.XFormInputView);

        isMustInput = typedArray.getBoolean(R.styleable.XFormInputView_form_is_must_input, false);
        mInputType = typedArray.getInteger(R.styleable.XFormInputView_form_input_type, InputType.TYPE_CLASS_TEXT);
        maxLength = typedArray.getInteger(R.styleable.XFormInputView_form_max_length, 0);
        headerWidth = typedArray.getDimensionPixelSize(R.styleable.XFormInputView_form_header_width, XDensityUtils.dp2px(context, 90));

        mTileTxt = typedArray.getString(R.styleable.XFormInputView_form_title_txt);
        mInputTxt = typedArray.getString(R.styleable.XFormInputView_form_end_txt);
        mHintTxt = typedArray.getString(R.styleable.XFormInputView_form_hint_txt);
        if (TextUtils.isEmpty(mHintTxt)) mHintTxt = "请输入" + mTileTxt;

        mTileTxtSize = typedArray.getDimension(R.styleable.XFormInputView_form_title_txt_size, XDensityUtils.sp2px(context, 14));
        mInputTxtSize = typedArray.getDimension(R.styleable.XFormInputView_form_end_txt_size, XDensityUtils.sp2px(context, 14));

        mTileTxtColor = typedArray.getColor(R.styleable.XFormInputView_form_title_txt_color, 0xff2a2a2a);
        mInputTxtColor = typedArray.getColor(R.styleable.XFormInputView_form_end_txt_color, 0xff2a2a2a);
        mHintTxtColor = typedArray.getColor(R.styleable.XFormInputView_form_edit_hint_color, 0xffa2a2a2);
        mEndTxtGravity = typedArray.getInt(R.styleable.XFormInputView_form_end_txt_gravity, Gravity.LEFT);
        typedArray.recycle();
        init(context, attrs);
    }


    private void init(Context context, @Nullable AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.layout_from_input_cblibrary, this);
        tvHeader = findViewById(R.id.tv_header);
        mEditText = findViewById(R.id.editText);

        tvHeader.setIsMust(isMustInput);
        tvHeader.setmText(mTileTxt);
        tvHeader.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTileTxtSize);
        tvHeader.setTextColor(mTileTxtColor);
        tvHeader.setLayoutParams(new LinearLayout.LayoutParams(headerWidth, LayoutParams.MATCH_PARENT));
        //
//        mEditText.setInputType(mInputType);
        if (TextUtils.isEmpty(mInputTxt)) {
            mEditText.setHint(mHintTxt);
        } else {
            mEditText.setText(mInputTxt);
        }
//        mEditText.setHorizontallyScrolling(false);
        mEditText.setInputType(mInputType);
//        mEditText.setKeyListener(DigitsKeyListener.getInstance("0123"));
        mEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mInputTxtSize);
        mEditText.setTextColor(mInputTxtColor);
        mEditText.setHintTextColor(mHintTxtColor);
        if (maxLength != 0) {
            mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        }
        mEditText.setGravity(Gravity.CENTER_VERTICAL | mEndTxtGravity);
    }

    public EditText getContentView() {
        return mEditText;
    }

    public boolean isMustInput() {
        return isMustInput;
    }

    public void setustInput(boolean mustInput) {
        isMustInput = mustInput;
        tvHeader.setIsMust(isMustInput);
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
    }

    public String getTileTxt() {
        return mTileTxt;
    }

    public void setTileTxt(String mTileTxt) {
        this.mTileTxt = mTileTxt;
        tvHeader.setText(mTileTxt);
    }

    public float getTileTxtSize() {
        return mTileTxtSize;
    }

    public void setTileTxtSize(float mTileTxtSize) {
        this.mTileTxtSize = mTileTxtSize;
        tvHeader.setTextSize(mTileTxtSize);
    }

    public void setHintTxt(String txt) {
        this.mHintTxt = txt;
        mEditText.setHint(mHintTxt);
    }

    public int getTileTxtColor() {
        return mTileTxtColor;
    }

    public void setTileTxtColor(@ColorInt int mTileTxtColor) {
        this.mTileTxtColor = mTileTxtColor;
        tvHeader.setTextColor(mTileTxtColor);
    }

    public void setHintTxtColor(@ColorInt int color) {
        this.mHintTxtColor = color;
        mEditText.setHintTextColor(mHintTxtColor);
    }

    public String getText() {
        return mEditText.getText().toString();
    }

    public void setText(String mEndTxt) {
        this.mInputTxt = mEndTxt;
        mEditText.setText(mEndTxt);
    }

    public float getEndTxtSize() {
        return mInputTxtSize;
    }

    public void setEndTxtSize(float mEndTxtSize) {
        this.mInputTxtSize = mEndTxtSize;
        mEditText.setTextSize(mEndTxtSize);
    }

    public void setInputType(int type) {
        this.mInputType = type;
        mEditText.setInputType(mInputType);
    }

    public int getEndTxtColor() {
        return mInputTxtColor;
    }

    public void setEndTxtColor(@ColorInt int mEndTxtColor) {
        this.mInputTxtColor = mEndTxtColor;
        mEditText.setTextColor(mEndTxtColor);
    }

//    //输入类型为没有指定明确的类型的特殊内容类型
//editText.setInputType(InputType.TYPE_NULL);
//
////输入类型为普通文本
//editText.setInputType(InputType.TYPE_CLASS_TEXT);
//
////输入类型为数字文本
//editText.setInputType(InputType.TYPE_CLASS_NUMBER);
//
////输入类型为电话号码
//editText.setInputType(InputType.TYPE_CLASS_PHONE);
//
////输入类型为日期和时间
//editText.setInputType(InputType.TYPE_CLASS_DATETIME);
//
////输入类型为{@link#TYPE_CLASS_DATETIME}的缺省变化值，允许输入日期和时间。
//editText.setInputType(InputType.TYPE_DATETIME_VARIATION_NORMAL);
//
////输入类型为{@link#TYPE_CLASS_DATETIME}的缺省变化值，只允许输入一个日期。
//editText.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
//
////输入类型为{@link#TYPE_CLASS_DATETIME}的缺省变化值，只允许输入一个时间。
//editText.setInputType(InputType.TYPE_DATETIME_VARIATION_TIME);
//
////输入类型为决定所给文本整体类的位掩码
//editText.setInputType(InputType.TYPE_MASK_CLASS);
//
////输入类型为提供附加标志位选项的位掩码
//editText.setInputType(InputType.TYPE_MASK_FLAGS);
//
////输入类型为决定基类内容变化的位掩码
//editText.setInputType(InputType.TYPE_MASK_VARIATION);
//
////输入类型为小数数字，允许十进制小数点提供分数值。
//editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
////输入类型为数字是带符号的，允许在开头带正号或者负号
//editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
//
////输入类型为{@link#TYPE_CLASS_NUMBER}的缺省变化值：为纯普通数字文本
//editText.setInputType(InputType.TYPE_NUMBER_VARIATION_NORMAL);
//
////输入类型为{@link#TYPE_CLASS_NUMBER}的缺省变化值：为数字密码
//editText.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
//
////输入类型为自动完成文本类型
//editText.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
//
////输入类型为自动纠正文本类型
//editText.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_CORRECT);
//
////输入类型为所有字符大写
//editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
//
////输入类型为每句的第一个字符大写
//editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
//
////输入类型为每个单词的第一个字母大写
//editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
//
////输入多行文本
//editText.setInputType(InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);
//
////进行输入时，输入法无提示
//editText.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
//
////输入一个短的，可能是非正式的消息，如即时消息或短信。
//editText.setInputType(InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE);
//
////输入长内容，可能是正式的消息内容，比如电子邮件的主体
//editText.setInputType(InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE);
//
////输入文本以过滤列表等内容
//editText.setInputType(InputType.TYPE_TEXT_VARIATION_FILTER);
//
////输入一个电子邮件地址
//editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
//
////输入电子邮件主题行
//editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);
//
////输入一个密码
//editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
//
////输入老式的普通文本
//editText.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
//
////输入人名
//editText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
//
////输入邮寄地址
//editText.setInputType(InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS);
//
////输入语音发音输入文本，如联系人拼音名称字段
//editText.setInputType(InputType.TYPE_TEXT_VARIATION_PHONETIC);
//
////输入URI
//editText.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
//
////输入对用户可见的密码
//editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//
////输入网页表单中的文本
//editText.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT);
//
////输入网页表单中的邮件地址
//editText.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
//
////输入网页表单中的密码
//editText.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
}
