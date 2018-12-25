package com.example.cb.test.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.cb.xlibrary.dialog.XLoadingDialog;


/**
 *
 */
public abstract class BaseFragment extends Fragment {

    private View mRootView;
    private Toast toast = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getRootViewId(), container, false);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent则从parent删除，防止发生这个rootview已经有parent的错误。
        ViewGroup mViewGroup = (ViewGroup) mRootView.getParent();
        if (mViewGroup != null) {
            mViewGroup.removeView(mRootView);
        }
        initUI(mRootView);
        return mRootView;
    }


    /**
     * 设置显示title
     *
     * @param title
     */
    protected void setHeaderTitle(String title, TextView view) {
        if (TextUtils.isEmpty(title)) {
            return;
        }
        view.setText(title);
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

    }

    @Override
    public void onDestroy() {
        closeInput();
        super.onDestroy();
    }


    /**
     * 信息提示框
     *
     * @param object
     */
    public void toast(Object object) {
        if (toast == null) {
            toast = Toast.makeText(getContext(), object.toString(), Toast.LENGTH_SHORT);
        } else {
            toast.setText(object.toString());
        }
        toast.show();
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
    }

    public abstract int getRootViewId();

    public abstract void initUI(View v);
}
