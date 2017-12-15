package com.cb.xlibrary.version;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cb.xlibrary.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.text.NumberFormat;


/**
 * author : caobin
 * e-mail : 821651400@qq.com
 * time   : 2017/12/14
 * desc   :带下载功能的版本检查dialog
 */
public class DownLoadApkDialog extends Dialog {

    private View mRootView;
    private ProgressBar mProgressBar;
    private TextView tvProgress;
    private String apkUrl = "";//app地址
    private String apkSavePath = "";//app下载保存路径
    private String apkName = "";
    private NumberFormat numberFormat;
    private boolean isDownLoadComplete = false;
    private DownLoadListener downLoadListener;

    public DownLoadApkDialog(@NonNull Context context, DownLoadListener listener) {
        super(context, R.style.XDialog);
        this.downLoadListener = listener;
        getWindow().setGravity(Gravity.CENTER);
        setCanceledOnTouchOutside(false);
        initView(context);
        setContentView(mRootView);
    }

    /**
     *
     */
    private void initView(Context context) {
        mRootView = View.inflate(context, R.layout.down_loading_layout, null);
        mProgressBar = (ProgressBar) mRootView.findViewById(R.id.pb);
        tvProgress = (TextView) mRootView.findViewById(R.id.tv_progress);
        numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMinimumFractionDigits(0);
    }

    /**
     * 开始下载
     */
    private void startDownLoad() {
        OkGo.<File>get(apkUrl)//
                .tag(this)//
                .execute(new FileCallback(apkSavePath, apkName) {

                    @Override
                    public void onSuccess(Response<File> response) {
                        isDownLoadComplete = true;
                        if (downLoadListener != null)
                            downLoadListener.downComplete(apkSavePath, apkName);
                        dismiss();
                    }

                    @Override
                    public void onError(Response<File> response) {
                        isDownLoadComplete = true;
                        dismiss();
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        tvProgress.setText(numberFormat.format(progress.fraction));
                        mProgressBar.setMax(10000);
                        mProgressBar.setProgress((int) (progress.fraction * 10000));
                    }
                });
    }

    @Override
    public void show() {
        super.show();
        startDownLoad();
    }

    @Override
    public void dismiss() {
        if (isDownLoadComplete) {
            OkGo.getInstance().cancelTag(this);
            super.dismiss();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && isDownLoadComplete) {
            dismiss();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 设置apk下载地址
     *
     * @param apkUrl
     */
    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    /**
     * 设置apk保存路径
     *
     * @param apkSavePath
     */
    public void setApkSavePath(String apkSavePath) {
        this.apkSavePath = apkSavePath;
    }

    /**
     * 设置apk保存路径
     *
     * @param apkName
     */
    public void setApkName(String apkName) {
        this.apkName = apkName;
    }
}
