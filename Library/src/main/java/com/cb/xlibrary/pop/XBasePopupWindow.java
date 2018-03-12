package com.cb.xlibrary.pop;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;


/**
 * 弹窗公共类
 * Created by caobin on 2017/5/2.
 */

public class XBasePopupWindow extends PopupWindow {
    final XPopupController controller;

    @Override
    public int getWidth() {
        return controller.mPopupView.getMeasuredWidth();
    }

    @Override
    public int getHeight() {
        return controller.mPopupView.getMeasuredHeight();
    }

    public interface ViewInterface {
        void getChildView(View view, int layoutResId);
    }

    private XBasePopupWindow(Context context) {
        controller = new XPopupController(context, this);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        controller.setBackGroundLevel(1.0f);
    }

    public static class Builder {
        private final XPopupController.PopupParams params;
        private ViewInterface listener;

        public Builder(Context context) {
            params = new XPopupController.PopupParams(context);
        }

        /**
         * @param layoutResId 设置PopupWindow 布局ID
         * @return Builder
         */
        public Builder setView(int layoutResId) {
            params.mView = null;
            params.layoutResId = layoutResId;
            return this;
        }

        /**
         * @param view 设置PopupWindow布局
         * @return Builder
         */
        public Builder setView(View view) {
            params.mView = view;
            params.layoutResId = 0;
            return this;
        }

        /**
         * 设置子View
         *
         * @param listener ViewInterface
         * @return Builder
         */
        public Builder setViewOnclickListener(ViewInterface listener) {
            this.listener = listener;
            return this;
        }

        /**
         * 设置宽度和高度 如果不设置 默认是wrap_content
         *
         * @param width 宽
         * @return Builder
         */
        public Builder setWidthAndHeight(int width, int height) {
            params.mWidth = width;
            params.mHeight = height;
            return this;
        }

        /**
         * 设置背景灰色程度
         *
         * @param level 0.0f-1.0f
         * @return Builder
         */
        public Builder setBackGroundLevel(float level) {
            params.isShowBg = true;
            params.bg_level = level;
            return this;
        }

        /**
         * 是否可点击Outside消失
         *
         * @param touchable 是否可点击
         * @return Builder
         */
        public Builder setOutsideTouchable(boolean touchable) {
            params.isTouchable = touchable;
            return this;
        }

        /**
         * 设置动画
         *
         * @return Builder
         */
        public Builder setAnimationStyle(int animationStyle) {
            params.isShowAnim = true;
            params.animationStyle = animationStyle;
            return this;
        }

        public XBasePopupWindow create() {
            final XBasePopupWindow popupWindow = new XBasePopupWindow(params.mContext);
            params.apply(popupWindow.controller);
            if (listener != null && params.layoutResId != 0) {
                listener.getChildView(popupWindow.controller.mPopupView, params.layoutResId);
            }
            //MyUtils.measureWidthAndHeight(popupWindow.controller.mPopupView);
            return popupWindow;
        }
    }
}
