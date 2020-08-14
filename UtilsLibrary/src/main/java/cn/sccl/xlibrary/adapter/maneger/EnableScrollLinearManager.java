package cn.sccl.xlibrary.adapter.maneger;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2019/9/30 11:29
 * @Desc :控制RV是否可以滑动
 * ====================================================
 */
public class EnableScrollLinearManager extends LinearLayoutManager implements XLayoutManager {

    private boolean canScroll = true;

    public EnableScrollLinearManager(Context context) {
        super(context);
    }

    public EnableScrollLinearManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public EnableScrollLinearManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
