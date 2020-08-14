package cn.sccl.xlibrary.dialog;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.ColorInt;

import com.cb.xlibrary.R;

import cn.sccl.xlibrary.utils.XOutdatedUtils;
import cn.sccl.xlibrary.widget.XColorDrawable;


/**
 * 加载等待提示框
 */
public class XLoadingDialog extends Dialog {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    private static XLoadingDialog dialog;
    private Context context;
    private TextView loadingMessage;
    private ProgressBar progressBar;
    private LinearLayout loadingView;
    private XColorDrawable drawable;

    public XLoadingDialog(Context context) {
        super(context, R.style.xloading_dialog_style_cblibrary);
        this.context = context;
        drawable = new XColorDrawable();
        setContentView(R.layout.xloading_dialog_cblibrary);
        loadingMessage = findViewById(R.id.xframe_loading_message);
        progressBar = findViewById(R.id.xframe_loading_progressbar);
        loadingView = findViewById(R.id.xframe_loading_view);
        loadingMessage.setPadding(15, 0, 0, 0);
        drawable.setColor(Color.WHITE);
        XOutdatedUtils.setBackground(loadingView, drawable);
    }

    public static XLoadingDialog with(Context context) {
        if (dialog == null) {
            dialog = new XLoadingDialog(context);
        }
        return dialog;
    }

    public XLoadingDialog setOrientation(int orientation) {
        loadingView.setOrientation(orientation);
        if (orientation == HORIZONTAL) {
            loadingMessage.setPadding(15, 0, 0, 0);
        } else {
            loadingMessage.setPadding(0, 15, 0, 0);
        }
        return dialog;
    }

    public XLoadingDialog setBackgroundColor(@ColorInt int color) {
        drawable.setColor(color);
        XOutdatedUtils.setBackground(loadingView, drawable);
        return dialog;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (dialog != null)
            dialog = null;
    }

    public XLoadingDialog setCanceled(boolean cancel) {
        setCanceledOnTouchOutside(cancel);
        setCancelable(cancel);
        return dialog;
    }

    public XLoadingDialog setMessage(String message) {
        if (!TextUtils.isEmpty(message)) {
            loadingMessage.setText(message);
        }
        return this;
    }

    public XLoadingDialog setMessageColor(@ColorInt int color) {
        loadingMessage.setTextColor(color);
        return this;
    }
}
