package com.cb.xlibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
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

    //    private List<BottomPopupBean> menusList;
    private XMenuListener mMenuListener;
    private TextView titleTv;//标题
    private boolean isDismissing;//动画是否结束

    public XActionSheetDialog(@NonNull Context context) {
        super(context, R.style.XDialog);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0); //消除边距
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;   //
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        initView(context);
    }

    private void initView(Context context) {
        mRootView = View.inflate(context, R.layout.layout_bottom_pop_view, null);
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler_view);
        titleTv = (TextView) mRootView.findViewById(R.id.tv_title);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new MyAdapter(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mRootView.findViewById(R.id.tv_cancel).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        cancel();
                    }
                });
        setContentView(mRootView);
        initAnim(context);
//        setOnCancelListener(new OnCancelListener() {
//
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                if (mMenuListener != null) {
//                    mMenuListener.onCancel();
//                }
//            }
//        });
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
                        dismissMe();
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
            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mMenuListener != null) mMenuListener.onItemSelected(position, data);
                    dismiss();
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
//        this.menusList = menusList;
        mAdapter.setDataLists(menusList);
    }

    /**
     * 添加标题
     */
    public void setPopTitle(String title) {
        titleTv.setText(title);
    }

}
