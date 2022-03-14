package com.cb.test.composemodule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import cn.sccl.xlibrary.dialog.XLoadingDialog;
import cn.sccl.xlibrary.dialog.XTipDialog;
import cn.sccl.xlibrary.utils.XPermission;
import cn.sccl.xlibrary.widget.XToast;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2019/9/5 15:03
 * @Desc :
 * ====================================================
 */
public abstract class ComposeBaseActivity extends AppCompatActivity {

    private Activity mCurrentActivity;
    private static long mPreTime;
    private XTipDialog mTipDialog;
    private XLoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        beforeInitUI();
        initUI();
        initEvent();
        backAction();
    }

    /**
     * 提供一个方法，让子类可以在initUI之前初始化一些东西
     */
    public void beforeInitUI() {
    }

    /**
     * 设置显示title
     *
     * @param title
     */
    protected void setHeaderTitle(String title) {
        if (isNull(title)) return;
        TextView textView = findViewById(R.id.title);
        if (textView != null)
            textView.setText(title);
    }

    /**
     * 默认的返回
     */
    protected void backAction() {
        View view = findViewById(R.id.iv_back);
        if (view != null) {
            view.setOnClickListener(v -> {
                finish();
            });
        }
    }

    /**
     * 标题右侧文字
     *
     * @param txt
     */
    protected void setRightTxt(String txt) {
        if (isNull(txt)) return;
        TextView view = findViewById(R.id.tvTitleRight);
        if (view == null) return;
        view.setText(txt);
    }


    public void backAction(View view) {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCurrentActivity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCurrentActivity = null;
    }

    /**
     * 跳转activity
     *
     * @param cls    跳转的类
     * @param bundle 携带的数据
     */
    public void launchActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转activity
     *
     * @param cls
     * @param bundle
     * @param requestCode 请求CODE
     */
    protected void launchActivityByCode(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 普通的Toast
     *
     * @param msg
     */
    public void toast(String msg) {
        if (isNull(msg)) return;
        XToast.normal(msg);
    }

    /**
     * 失败的Toast，背景红色的
     *
     * @param msg
     */
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
        return content == null
                || TextUtils.isEmpty(content)
                || "null".equalsIgnoreCase(content);
    }

    /**
     * 弹出自定义AlertDialog
     *
     * @param msg
     */
    protected void showAlert(String msg) {
        if (mTipDialog != null) {
            mTipDialog.dismiss();
            mTipDialog = null;
        }
        mTipDialog = new XTipDialog.Builder(this)
                .title("温馨提示")
                .setMessage(msg == null ? "" : msg)
                .setCancelable(false)
                .create();
        mTipDialog.show();
    }


    /**
     * 加载弹窗,点击外部可以取消
     */
    protected void showDLG() {
        showDLG(true);
    }

    /**
     * 加载弹窗
     *
     * @param isCancel 是否点击外部可以取消
     */
    protected void showDLG(boolean isCancel) {
        showDlgWithMsg("努力加载中...", isCancel);
    }

    /**
     * 加载自定义文字弹窗,点其他地方消失
     */
    protected void showDlgWithMsg(String msg) {
        showDlgWithMsg(msg, true);
    }

    /**
     * 加载自定义文字弹窗
     *
     * @param msg
     * @param isCancel 是否点击外部可以取消
     */
    protected void showDlgWithMsg(String msg, boolean isCancel) {
        try {
            mLoadingDialog = new XLoadingDialog(this);
            mLoadingDialog.setBackgroundColor(Color.parseColor("#333333"));
            mLoadingDialog.setMessageColor(Color.parseColor("#DDDDDD"));
            mLoadingDialog.setMessage(msg.equals("") ? "努力加载中..." : msg);
            mLoadingDialog.setOrientation(XLoadingDialog.VERTICAL);
            mLoadingDialog.setCanceled(isCancel);
            mLoadingDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 弹窗消失
     */
    protected void disMissDLG() {
        try {
            if (mLoadingDialog != null && mLoadingDialog.isShowing()) mLoadingDialog.dismiss();
        } catch (Exception e) {
        }
    }


    @Override
    protected void onDestroy() {
        closeInput();
        try {
            if (XLoadingDialog.with(this) != null) {
                XLoadingDialog.with(this).dismiss();
            }
            if (mTipDialog != null) {
                mTipDialog.dismiss();
                mTipDialog = null;
            }
            if (mLoadingDialog != null) {
                mLoadingDialog.dismiss();
                mLoadingDialog = null;
            }
        } catch (Exception e) {
        }
        super.onDestroy();
    }

    /**
     * 关闭键盘
     */
    public void closeInput() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * Android M 全局权限申请回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        XPermission.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected abstract int getLayoutId();

    protected abstract void initUI();

    protected abstract void initEvent();
}
