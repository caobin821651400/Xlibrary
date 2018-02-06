package com.cb.xlibrary.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

/**
 * author : caobin
 * e-mail : 821651400@qq.com
 * time   : 2017/12/15
 * desc   : 更新提示弹窗
 */
public class XCheckVersionAlert {
    private AlertDialog.Builder mAlertDialog;
    private Context mContext;
    private BtnClickListener btnClickListener;

    public XCheckVersionAlert(Context mContext) {
        this.mContext = mContext;
    }

    public void setBtnClickListener(BtnClickListener listener) {
        this.btnClickListener = listener;
    }

    /**
     * 更新弹窗
     *
     * @param updateMsg 更新说明
     * @param title     标题
     * @param isForce   是否强制更新
     */
    public void showUpdateAlert(String updateMsg, String title, boolean isForce) {
        SpannableString spanString = new SpannableString(updateMsg);
        spanString.setSpan(new ForegroundColorSpan(Color.parseColor("#878787")),
                0, updateMsg.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        if (mAlertDialog == null)
            mAlertDialog = new AlertDialog.Builder(mContext);
        mAlertDialog.setTitle(title);
        mAlertDialog.setMessage(spanString);
        mAlertDialog.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (btnClickListener != null) {
                    btnClickListener.update();
                }
            }
        });
        if (isForce) {
            mAlertDialog.setNegativeButton("退出程序", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (btnClickListener != null) {
                        btnClickListener.noUpdate();
                    }
                }
            });
        } else {
            mAlertDialog.setNegativeButton("以后再说", null);
        }
        mAlertDialog.setCancelable(false);
        mAlertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
        mAlertDialog.create();
        mAlertDialog.show();
    }


    public interface BtnClickListener {
        void noUpdate();

        void update();
    }
}
