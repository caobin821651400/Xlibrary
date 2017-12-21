package com.cb.xlibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.cb.xlibrary.R;
import com.cb.xlibrary.adapter.XRecyclerViewAdapter;
import com.cb.xlibrary.adapter.XViewHolder;
import com.cb.xlibrary.bean.BottomPopupBean;

import java.util.List;

/**
 * author : caobin
 * e-mail : 821651400@qq.com
 * time   : 2017/12/18
 * desc   : 底部弹窗
 */
public class XActionSheetDialog extends Dialog {

    private MyAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private View mRootView;
    private Animation mShowAnimation;
    private Animation mDismissAnimation;
    private int txtColor = -1;

    //    private List<BottomPopupBean> menusList;
    private XMenuListener mMenuListener;
    private TextView titleTv;//标题
    private TextView cancelTv;//标题
    private boolean isDismissing;//结束过程

    public XActionSheetDialog(@NonNull Context context) {
        super(context, R.style.XDialog);
        initView(context);
    }

    public XActionSheetDialog(@NonNull Context context, @ColorRes int color) {
        super(context, R.style.XDialog);
        this.txtColor = ContextCompat.getColor(context, color);
        initView(context);
    }

    private void initView(Context context) {
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0); //消除边距
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;   //
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        mRootView = View.inflate(context, R.layout.layout_bottom_pop_view, null);
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler_view);
        titleTv = (TextView) mRootView.findViewById(R.id.tv_title);
        cancelTv = (TextView) mRootView.findViewById(R.id.tv_cancel);
        if (txtColor != -1) {
            cancelTv.setTextColor(txtColor);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new MyAdapter(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        cancelTv.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        cancel();
                    }
                });
        setContentView(mRootView);
        initAnim(context);
    }

    /**
     * 设置动画
     *
     * @param context
     */
    private void initAnim(Context context) {
        mShowAnimation = AnimationUtils.loadAnimation(context,
                R.anim.popshow_anim);
        mDismissAnimation = AnimationUtils.loadAnimation(context,
                R.anim.pophidden_anim);
        mDismissAnimation
                .setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mRootView.post(new Runnable() {
                            @Override
                            public void run() {
                                dismissMe();
                            }
                        });
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
    }

    @Override
    public void dismiss() {
        if (isDismissing) {
            return;
        }
        isDismissing = true;
        mRootView.startAnimation(mDismissAnimation);
    }

    private void dismissMe() {
        super.dismiss();
        isDismissing = false;
    }

    @Override
    public void show() {
        super.show();
        mAdapter.notifyDataSetChanged();
        mRootView.startAnimation(mShowAnimation);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            dismiss();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 适配器
     */
    class MyAdapter extends XRecyclerViewAdapter<BottomPopupBean> {

        private TextView content;

        public MyAdapter(@NonNull RecyclerView mRecyclerView) {
            super(mRecyclerView, R.layout.layout_bottom_pop_items);
        }

        @Override
        protected void bindData(XViewHolder holder, final BottomPopupBean data, final int position) {
            View itemView = holder.getConvertView();
            content = (TextView) itemView.findViewById(R.id.tv_content);
            content.setText(data.getTitle());
            if (txtColor != -1) {
                content.setTextColor(txtColor);
            }
            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (mMenuListener != null) {
                        mMenuListener.onItemSelected(position, data);
                    }
                }
            });
        }
    }


    public interface XMenuListener {
        void onItemSelected(int position, BottomPopupBean item);
    }

    /**
     * 监听
     *
     * @param listener
     */
    public void setOnItemClickListener(XMenuListener listener) {
        this.mMenuListener = listener;
    }

    /**
     * 设置列表
     *
     * @param menusList
     */
    public void setMenusList(List<BottomPopupBean> menusList) {
        mAdapter.setDataLists(menusList);
    }

    /**
     * 添加标题
     */
    public void setPopTitle(String title) {
        titleTv.setText(title);
    }

}
