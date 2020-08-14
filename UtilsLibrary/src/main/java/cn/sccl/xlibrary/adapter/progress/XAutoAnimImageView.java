package cn.sccl.xlibrary.adapter.progress;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2019/9/30 10:48
 * @Desc :当imageView加载到布局时自动实现动画
 * ====================================================
 */
public class XAutoAnimImageView extends AppCompatImageView {

    XProgressDrawable mProgressDrawable;


    public XAutoAnimImageView(Context context) {
        this(context, null);
    }

    public XAutoAnimImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XAutoAnimImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (mProgressDrawable == null) {
            mProgressDrawable = new XProgressDrawable();
            mProgressDrawable.setColor(0xff666666);
        }
        setImageDrawable(mProgressDrawable);
    }

//    @Override
//    protected void onAttachedToWindow() {
//        super.onAttachedToWindow();
//        Drawable drawable = getDrawable();
//        if (drawable != null) {
//            ((Animatable) drawable).start();
//        }
//    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        Drawable drawable = getDrawable();
        if (drawable == null) return;
        if (visibility == VISIBLE) {
            ((Animatable) drawable).start();
        } else {
            ((Animatable) drawable).stop();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animate().cancel();
        final Drawable drawable = getDrawable();
        if (drawable != null && ((Animatable) drawable).isRunning()) {
            ((Animatable) drawable).stop();
        }
    }
}
