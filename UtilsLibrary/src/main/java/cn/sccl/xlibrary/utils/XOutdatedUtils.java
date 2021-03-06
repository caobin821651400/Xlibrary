package cn.sccl.xlibrary.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import android.view.View;


/**
 * 此类主要是用来放一些系统过时方法的处理
 */
public class XOutdatedUtils {

    private XOutdatedUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * setBackgroundDrawable过时方法处理
     *
     * @param view
     * @param drawable
     */
    public static void setBackground(@NonNull View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            view.setBackground(drawable);
        else
            view.setBackgroundDrawable(drawable);
    }

    /**
     * getDrawable过时方法处理
     *
     * @param id
     * @return
     */
    public static Drawable getDrawable(Context context, @DrawableRes int id) {
        return ContextCompat.getDrawable(context.getApplicationContext(), id);
    }

    /**
     * getDrawable过时方法处理
     *
     * @param id    资源id
     * @param theme 指定主题
     * @return
     */
    public static Drawable getDrawable(Context context, @DrawableRes int id,
                                       @Nullable Resources.Theme theme) {
        return ResourcesCompat.getDrawable(context.getApplicationContext().getResources(), id, theme);
    }

    /**
     * getColor过时方法处理
     *
     * @param id
     * @return
     */
    public static int getColor(Context context, @ColorRes int id) {
        return ContextCompat.getColor(context, id);
    }

    /**
     * getColor过时方法处理
     *
     * @param id    资源id
     * @param theme 指定主题
     * @return
     */
    public static int getColor(Context context, @ColorRes int id, @Nullable Resources.Theme theme) {
        return ResourcesCompat.getColor(context.getApplicationContext().getResources(), id, theme);
    }

    /**
     * getColorStateList过时方法处理
     *
     * @param id 资源id
     * @return
     */
    public static ColorStateList getColorStateList(Context context, @ColorRes int id) {
        return ContextCompat.getColorStateList(context.getApplicationContext(), id);
    }

    /**
     * getColorStateList过时方法处理
     *
     * @param id    资源id
     * @param theme 指定主题
     * @return
     */
    public static ColorStateList getColorStateList(Context context, @ColorRes int id, @Nullable Resources.Theme theme) {
        return ResourcesCompat.getColorStateList(context.getApplicationContext().getResources(), id, theme);
    }
}
