package com.cb.xlibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cb.xlibrary.R;


/**
 * author : caobin
 * e-mail : 821651400@qq.com
 * time   : 2017/12/14
 * desc   :下载进度条
 */
public class XDownLoadDialog extends Dialog {

    private View mRootView;
    private ProgressBar mProgressBar;
    private TextView tvProgress;

    public XDownLoadDialog(@NonNull Context context) {
        super(context, R.style.XDialog);
        setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                dismiss();
            }
        });
        getWindow().setGravity(Gravity.CENTER);
        setCancelable(false);
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
    }

    public ProgressBar getProgressBar() {
        return mProgressBar;
    }

    public TextView getProgressTextView() {
        return tvProgress;
    }
}
