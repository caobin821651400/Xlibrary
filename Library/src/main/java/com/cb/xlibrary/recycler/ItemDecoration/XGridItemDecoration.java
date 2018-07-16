package com.cb.xlibrary.recycler.ItemDecoration;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.View;

import com.cb.xlibrary.adapter.XRecyclerViewAdapter;
import com.cb.xlibrary.utils.XLogUtils;

/**
 * RecyclerView的GridItem分割线
 */
public class XGridItemDecoration extends RecyclerView.ItemDecoration {
    private int verticalSpace;
    private int horizontalSpace;
    private Paint mPaint;

    public XGridItemDecoration(int horizontalSpace, int verticalSpace, int colour) {
        this.horizontalSpace = horizontalSpace;
        this.verticalSpace = verticalSpace;
        mPaint = new Paint();
        mPaint.setColor(colour);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, State state) {
        drawHorizontal(c, parent);
        drawVertical(c, parent);
    }

    /**
     * 获取列数
     *
     * @param parent
     * @return
     */
    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }

    /**
     * 画水平的分割线
     *
     * @param c
     * @param parent
     */
    public void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        XRecyclerViewAdapter adapter = (XRecyclerViewAdapter) parent.getAdapter();
        for (int i = 0; i < childCount; i++) {
            if ((adapter.isHeaderPosition(i) || adapter.isFooterPosition(i))) {
                c.drawRect(0, 0, 0, 0, mPaint);
            } else {//this
                View child = parent.getChildAt(i);
                int top = child.getBottom();
                int bottom = top + verticalSpace;
                int left = child.getLeft();
                int right = child.getRight();
                c.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    /**
     * 画垂直的分割线
     *
     * @param c
     * @param parent
     */
    public void drawVertical(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        XRecyclerViewAdapter adapter = (XRecyclerViewAdapter) parent.getAdapter();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            //
            if (adapter.isHeaderPosition(i) || adapter.isFooterPosition(i)) {
                continue;
            } else {
                if (isLastColumn(parent, i, getSpanCount(parent))) {//右边的画一条
                    int childTop = child.getTop();
                    int childBottom = child.getBottom() + verticalSpace;
                    int childLeft1 = child.getLeft() - params.leftMargin - horizontalSpace;
                    int childRight1 = childLeft1 + horizontalSpace;
                    int childLeft2 = child.getRight();
                    int childRight2 = childLeft2 + horizontalSpace;
                    c.drawRect(childLeft1, childTop, childRight1, childBottom, mPaint);
                    c.drawRect(childLeft2, childTop, childRight2, childBottom, mPaint);
                } else {//左边的
                    int divLineTop = child.getTop();
                    int divLineBottom = child.getBottom() + verticalSpace;
                    int divLineLeft = child.getLeft() - params.leftMargin - horizontalSpace;
                    int divLineRight = divLineLeft + horizontalSpace;
                    c.drawRect(divLineLeft, divLineTop, divLineRight, divLineBottom, mPaint);
                }
            }
        }
    }

    /**
     * 是否为最后一行
     *
     * @param parent     RecyclerView
     * @param pos        当前item的位置
     * @param spanCount  每行显示的item个数
     * @param childCount child个数
     */
    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount, int childCount) {
        LayoutManager layoutManager = parent.getLayoutManager();
        XRecyclerViewAdapter adapter = (XRecyclerViewAdapter) parent.getAdapter();
        childCount = childCount - adapter.getHeaderCount();
        if (childCount % spanCount == 0) {
            childCount = childCount - 2;
        }
        if (layoutManager instanceof GridLayoutManager) {
            int count = childCount - childCount % spanCount;
            //leftCount:若childCount能被span整除为childCount否则为去掉最后一行的item总数
            if ((pos - adapter.getHeaderCount() + 1) > count) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否为最后一列
     *
     * @param parent
     * @param pos
     * @param spanCount
     * @return
     */
    private boolean isLastColumn(RecyclerView parent, int pos, int spanCount) {
        LayoutManager layoutManager = parent.getLayoutManager();
        XRecyclerViewAdapter adapter = (XRecyclerViewAdapter) parent.getAdapter();
        if (layoutManager instanceof GridLayoutManager) {
            if ((pos - adapter.getHeaderCount() + 1) % spanCount == 0)
                // 如果是最后一列，则不需要绘制右边
                return true;
        }
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int itemPosition = parent.getChildAdapterPosition(view);
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();
        XRecyclerViewAdapter adapter = (XRecyclerViewAdapter) parent.getAdapter();
        if (adapter.isFooterPosition(itemPosition) || adapter.isHeaderPosition(itemPosition)) {
            //header，footer不进行绘制
            outRect.set(0, 0, 0, 0);
        } else {
            if (isLastRaw(parent, itemPosition, spanCount, childCount)) {//最后一行
                if (isLastColumn(parent, itemPosition, spanCount)) {//最后一行最后一列
                    outRect.set(horizontalSpace, 0, horizontalSpace, 0);
                } else {
                    outRect.set(horizontalSpace, 0, 0, 0);
                }
            } else {//不是最后一行
                if (isLastColumn(parent, itemPosition, spanCount)) {//不是最后一行的最后一列
                    outRect.set(horizontalSpace, 0, horizontalSpace, verticalSpace);
                } else {
                    outRect.set(horizontalSpace, 0, 0, verticalSpace);
                }
            }
        }
    }

    public static class Builder {
        private Context mContext;
        private Resources mResources;
        private int mHorizontal;
        private int mVertical;


        private int mColour;

        public Builder(Context context) {
            mContext = context;
            mResources = context.getResources();
            mHorizontal = 0;
            mVertical = 0;
            mColour = Color.BLACK;
        }


        /**
         * Sets the divider colour
         *
         * @param resource the colour resource id
         * @return the current instance of the Builder
         */
        public Builder setColorResource(@ColorRes int resource) {
            setColor(ContextCompat.getColor(mContext, resource));
            return this;
        }

        /**
         * Sets the divider colour
         *
         * @param color the colour
         * @return the current instance of the Builder
         */
        public Builder setColor(@ColorInt int color) {
            mColour = color;
            return this;
        }


        //通过dp设置垂直间距
        public Builder setVertical(@DimenRes int vertical) {
            this.mVertical = mResources.getDimensionPixelSize(vertical);
            return this;
        }

        //通过px设置垂直间距
        public Builder setVertical(float mVertical) {
            this.mVertical = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, mVertical, mResources.getDisplayMetrics());
            return this;
        }

        //通过dp设置水平间距
        public Builder setHorizontal(@DimenRes int horizontal) {
            this.mHorizontal = mResources.getDimensionPixelSize(horizontal);
            return this;
        }

        //通过px设置水平间距
        public Builder setHorizontal(float horizontal) {
            this.mHorizontal = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, horizontal, mResources.getDisplayMetrics());
            return this;
        }

        /**
         * Instantiates a DividerDecoration with the specified parameters.
         *
         * @return a properly initialized DividerDecoration instance
         */
        public XGridItemDecoration build() {
            return new XGridItemDecoration(mHorizontal, mVertical, mColour);
        }
    }

}
