package com.example.cb.test.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Toast;

import com.example.cb.test.BuildConfig;
import com.example.cb.test.utils.DownloadUtil;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import cb.xlibrary.dialog.XDownLoadDialog;
import cb.xlibrary.utils.XAppUtils;


public class BaseVersionActivity extends BaseActivity {


    protected XDownLoadDialog mDownloadProgressDialog;
    protected File apkFile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mDownloadProgressDialog == null)
            mDownloadProgressDialog = new XDownLoadDialog(this);
    }

    /**
     * 检查版本更新
     */
    protected void checkNewVersion() {
//        OkGo.<VersionBean>get(Constants.BASE_URL + "cityService/dictionaries/queryVersion.htm")
//                .params("osname", "android")
//                .params("version", XAppUtils.getVersionName(this))
//                .execute(new JsonCallBack<VersionBean>() {
//                    @Override
//                    public void onSuccess(Response<VersionBean> response) {
//                        if (response.body().getCode().equals("0")) {//有新版本
//                            VersionBean.DataBean data = response.body().getData().get(0);
//                            //弹出提示版本更新的对话框
//                            showConfirmCheckVersion(data.getUrl(), "新版本号：" + data.getVersion() + "\n更新内容：\n"
//                                    + data.getDesc(), data.getType() == 1);
//                        }
//                    }
//                });

    }

    /**
     * 版本检查确认
     *
     * @param url
     * @param remarks
     * @param force   是否强制升级
     */
    public void showConfirmCheckVersion(final String url, String remarks, boolean force) {
        SpannableString spanString = new SpannableString(remarks);
        spanString.setSpan(new ForegroundColorSpan(Color.parseColor("#878787")), 0, remarks.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("发现新版本");
        builder.setMessage(spanString);
        builder.setCancelable(false);
        builder.setNegativeButton(force ? null : "以后再说", null);
        builder.setPositiveButton("立即升级", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    mDownloadProgressDialog.show();
                    DownloadUtil.getInstance().download(url, onDownloadListener, false);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(BaseVersionActivity.this, "对不起，下载出错。", Toast.LENGTH_SHORT).show();
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize(18);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
    }

    /**
     * 下载监听
     */
    DownloadUtil.OnDownloadListener onDownloadListener = new DownloadUtil.OnDownloadListener() {
        @Override
        public void onDownloadSuccess(String path) {
            mDownloadProgressDialog.dismiss();
            apkFile = new File(path);
            if (!apkFile.exists()) {
                toast("对不起，下载出错。");
                return;
            }
            if (path.contains(".apk")) {
                XAppUtils.installApk8(BaseVersionActivity.this, apkFile,
                        BuildConfig.APPLICATION_ID + ".fileprovider", 1945);
            }
        }

        @Override
        public void onDownloading(int progress) {
            mDownloadProgressDialog.getProgressBar().setProgress(progress);
        }

        @Override
        public void onDownloadFailed() {
            toast("对不起，下载出错。");
            mDownloadProgressDialog.dismiss();
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //8.0权限申请回来
        if (requestCode == 10012) {
            XAppUtils.installApk8(BaseVersionActivity.this, apkFile,
                    BuildConfig.APPLICATION_ID + ".fileProvider", 1945);
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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //8.0安装权限
        if (requestCode == 1945) {//直接安装
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                XAppUtils.installApk7(this, "", apkFile);
            } else {//跳转到设置权限界面
                Uri packageURI = Uri.parse("package:" + getPackageName());
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
                startActivityForResult(intent, 10012);
            }
        }
    }
}
