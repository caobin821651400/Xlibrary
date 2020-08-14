package cn.sccl.xlibrary.adapter.maneger;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.GridLayoutManager;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2019/9/30 11:29
 * @Desc :控制RV是否可以滑动
 * ====================================================
 */
public class EnableScrollGridManager extends GridLayoutManager implements XLayoutManager {

    private boolean canScroll = true;

    public EnableScrollGridManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public EnableScrollGridManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public EnableScrollGridManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    /**
     * 垂直方向
     *
     * @return
     */
    @Override
    public boolean canScrollVertically() {
        return canScroll && super.canScrollVertically();
    }

    /**
     * 水平方向
     *
     * @return
     */
    @Override
    public boolean canScrollHorizontally() {
        return super.canScrollHorizontally();
    }

    @Override
    public void enableScroll(boolean isScroll) {
        this.canScroll = isScroll;
    }
}