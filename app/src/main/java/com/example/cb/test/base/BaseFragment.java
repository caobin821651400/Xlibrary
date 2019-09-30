package com.example.cb.test.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import cb.xlibrary.dialog.XLoadingDialog;
import cb.xlibrary.dialog.XTipDialog;
import cb.xlibrary.widget.XToast;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2019/9/5 14:59
 * @Desc :
 * ====================================================
 */
public abstract class BaseFragment extends Fragment {

    private View mRootView;
    private XTipDialog mTipDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutId(), container, false);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent则从parent删除，防止发生这个rootview已经有parent的错误。
        ViewGroup mViewGroup = (ViewGroup) mRootView.getParent();
        if (mViewGroup != null) {
            mViewGroup.removeView(mRootView);
        }
        initUI(mRootView);
        openInterface();
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initEvent(view);
    }

    /**
     * 为了方便其他类创建自己的抽象方法，而不会影响{@link BaseFragment}的抽象方法
     */
    protected void openInterface() {
    }

    /**
     * 跳转activity
     *
     * @param cls
     * @param bundle
     */
    protected void launchActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(getActivity(), cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转activity传参
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    protected void launchActivityByCode(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(getActivity(), cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 关闭键盘
     */
    public void closeInput() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && getActivity().getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    /**
     * 弹出AlertDialog
     *
     * @param msg
     */
    protected void showAlert(String msg) {
        if (mTipDialog != null) {
            mTipDialog.dismiss();
            mTipDialog = null;
        }
        mTipDialog = new XTipDialog.Builder(getActivity())
                .title("温馨提示")
                .setMessage(msg == null ? "" : msg)
                .setCancelable(false)
                .create();
        mTipDialog.show();
    }

    @Override
    public void onDestroy() {
        closeInput();
        super.onDestroy();
    }


    public void toast(String msg) {
        if (isNull(msg)) return;
        XToast.normal(msg);
    }

    public void toastError(String msg) {
        if (isNull(msg)) return;
        XToast.error(msg);
    }

    /**
     * 判断String类型是否为空
     *
     * @return
     */
    public boolean isNull(String content) {
        return content == null || TextUtils.isEmpty(content) || "null".equalsIgnoreCase(content);
    }

    /**
     * 显示进度提示
     */
    protected void showDLG() {
        XLoadingDialog.with(getActivity())
                .setBackgroundColor(Color.parseColor("#ffffff"))
                .setMessageColor(Color.BLACK)
                .setMessage("努力加载中...")
                .setOrientation(XLoadingDialog.VERTICAL)
                .setCanceled(true)
                .show();
    }

    /**
     * 显示进度提示 外部不可取消
     */
    protected void showDLG1() {
        XLoadingDialog.with(getActivity())
                .setBackgroundColor(Color.parseColor("#ffffff"))
                .setMessageColor(Color.BLACK)
                .setMessage("努力加载中...")
                .setOrientation(XLoadingDialog.VERTICAL)
                .setCanceled(false)
                .show();
    }

    /**
     * 显示进度提示
     *
     * @param msg
     */
    protected void showDlgWithMsg(String msg) {
        XLoadingDialog.with(getActivity())
                .setBackgroundColor(Color.parseColor("#ffffff"))
                .setMessageColor(Color.BLACK)
                .setMessage(msg.equals("") ? "努力加载中..." : msg)
                .setOrientation(XLoadingDialog.VERTICAL)
                .setCanceled(true)
                .show();
    }

    /**
     * 关闭进度提示
     */
    protected void disMissDLG() {
        try {
            XLoadingDialog.with(getActivity()).dismiss();
        } catch (Exception e) {
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            if (mTipDialog != null) {
                mTipDialog.dismiss();
                mTipDialog = null;
            }
        } catch (Exception e) {

        }
    }

    protected abstract int getLayoutId();

    protected abstract void initUI(View v);

    protected abstract void initEvent(View view);
}
