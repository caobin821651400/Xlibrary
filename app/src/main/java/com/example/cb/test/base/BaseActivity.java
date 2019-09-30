package com.example.cb.test.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.cb.test.MainActivity;

import cb.xlibrary.dialog.XLoadingDialog;
import cb.xlibrary.dialog.XTipDialog;
import cb.xlibrary.utils.XPermission;
import cb.xlibrary.widget.XToast;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2019/9/5 15:03
 * @Desc :
 * ====================================================
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Activity mCurrentActivity;
    private static long mPreTime;
    private XTipDialog mTipDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initUI();
        initEvent();
    }

//    /**
//     * 设置显示title
//     *
//     * @param title
//     */
//    protected void setHeaderTitle(String title) {
//        if (TextUtils.isEmpty(title)) {
//            return;
//        }
//        TextView textView = (TextView) findViewById(R.id.tv_common_title);
//        textView.setText(title);
//    }

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
     * 显示进度提示
     */
    protected void showDLG() {
        XLoadingDialog.with(this)
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
        XLoadingDialog.with(this)
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
        XLoadingDialog.with(this)
                .setBackgroundColor(Color.parseColor("#ffffff"))
                .setMessageColor(Color.BLACK)
                .setMessage(msg.equals("") ? "努力加载中..." : msg)
                .setOrientation(XLoadingDialog.VERTICAL)
                .setCanceled(true)
                .show();
    }

    /**
     *
     */
    protected void disMissDLG() {
        try {
            XLoadingDialog.with(this).dismiss();
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
     * 统一退出控制
     */
    @Override
    public void onBackPressed() {
        if (mCurrentActivity instanceof MainActivity) {
            //如果是主页面
            if (System.currentTimeMillis() - mPreTime > 2000) {// 两次点击间隔大于2秒
                toast("再按一次，退出应用");
                mPreTime = System.currentTimeMillis();
                return;
            }
        }
        super.onBackPressed();// finish()
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
