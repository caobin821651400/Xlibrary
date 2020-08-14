package cn.sccl.xlibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Html;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.cb.xlibrary.R;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2019/10/12 10:54
 * @Desc :前面带红色*号的TextView，标识必填
 * ====================================================
 */
public class XAsteriskTextView extends AppCompatTextView {

    private boolean isMust;
    private String txt;

    public XAsteriskTextView(Context context) {
        this(context, null);
    }

    public XAsteriskTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XAsteriskTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AsteriskTextView);
        isMust = array.getBoolean(R.styleable.AsteriskTextView_isMustInput, false);
        txt = array.getString(R.styleable.AsteriskTextView_txt);
        array.recycle();

        setmText(txt);
    }

    /**
     * @param txt
     */
    public void setmText(String txt) {
        if (isMust) {
            setText(Html.fromHtml("<font color='#FF0000'> * </font>" + txt));
        } else {
            setText(txt);
        }
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (isMust){

        }
        super.setText(text, type);
    }

    /**
     * @return
     */
    public boolean isMust() {
        return isMust;
    }

    /**
     * @param must
     */
    public void setIsMust(boolean must) {
        isMust = must;
    }
}
