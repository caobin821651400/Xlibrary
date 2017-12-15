package com.cb.xlibrary.version;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import com.cb.xlibrary.utils.XActivityStack;

/**
 * author : caobin
 * e-mail : 821651400@qq.com
 * time   : 2017/12/15
 * desc   : 更新提示弹窗
 */
public class CheckVersionAlert {
    private AlertDialog.Builder mAlertDialog;
    private DownLoadListener downLoadListener;
    private Context mContext;

    public CheckVersionAlert(DownLoadListener downLoadListener, Context mContext) {
        this.downLoadListener = downLoadListener;
        this.mContext = mContext;
    }

    /**
     * 更新弹窗
     *
     * @param updateMsg 更新说明
     * @param title     标题
     * @param apkName
     * @param apkPath   保存路径
     * @param apkUrl    下载地址
     * @param isForce   是否强制更新
     */
    public void showUpdateAlert(String updateMsg, String title, final String apkName, final String apkPath, final String apkUrl, boolean isForce) {
        SpannableString spanString = new SpannableString(updateMsg);
        spanString.setSpan(new ForegroundColorSpan(Color.parseColor("#878787")), 0, updateMsg.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        mAlertDialog = new AlertDialog.Builder(mContext);
        mAlertDialog.setTitle(title);
        mAlertDialog.setMessage(spanString);
        mAlertDialog.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DownLoadApkDialog checkDialog = new DownLoadApkDialog(mContext, downLoadListener);
                checkDialog.setApkName(apkName);
                checkDialog.setApkSavePath(apkPath);
                checkDialog.setApkUrl(apkUrl);
                checkDialog.show();
            }
        });
        if (isForce) {
            mAlertDialog.setNegativeButton("退出程序", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    XActivityStack.getInstance().appExit();
                }
            });
        } else {
            mAlertDialog.setNegativeButton("以后再说", null);
        }
        mAlertDialog.setCancelable(false);
        mAlertDialog.create();
        mAlertDialog.show();
    }
}
