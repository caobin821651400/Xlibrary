package cn.sccl.xlibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

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
    private TextView tvTitleTxt;

    public XDownLoadDialog(@NonNull Context context) {
        super(context, R.style.Dialog_cblibrary);
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
        mRootView = View.inflate(context, R.layout.dialog_down_load_layout_cblibrary, null);
        mProgressBar = mRootView.findViewById(R.id.pb);
        tvTitleTxt = mRootView.findViewById(R.id.tv_dialog_txt_cblibrary);
        tvProgress = mRootView.findViewById(R.id.tv_progress);
    }

    public ProgressBar getProgressBar() {
        return mProgressBar;
    }

    public TextView getProgressTextView() {
        return tvProgress;
    }

    public TextView getTitleTextView() {
        return tvTitleTxt;
    }

    public void setTitle(String txt) {
        tvTitleTxt.setText(txt);
    }
}
