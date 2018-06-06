package com.cb.xlibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.cb.xlibrary.R;

/**
 * author : caobin
 * e-mail : 821651400@qq.com
 * time   : 2017/12/15
 * desc   :输入框弹窗
 */
public class XTipDialog extends Dialog {

    public interface BtnClickListener {
        void leftBtnClick();

        void rightBtnClick();
    }

    private Button btnRight;
    private Button btnLeft;
    private TextView tvContent;
    private TextView tvTitle;
    private BtnClickListener btnClickListener;

    private String title;
    private String leftBtnTxt = "";
    private String rightBtnTxt = "";
    private String message = "";
    private boolean isBgTrsnsparent = false;//背景半透明
    private int leftBtnTxtColor = Color.parseColor("#FF0000");
    private int rightBtnTxtColor = Color.parseColor("#000000");


    private XTipDialog(@NonNull Context context) {
        super(context, R.style.XDialog);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tip_dialog);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        btnRight = (Button) findViewById(R.id.btn_right);
        btnLeft = (Button) findViewById(R.id.btn_left);
        tvContent = (TextView) findViewById(R.id.tv_content);
    }

    @Override
    public void show() {
        super.show();
        show(this);
    }

    /**
     * @param xInputDialog
     */
    private void show(XTipDialog xInputDialog) {
        tvTitle.setText(xInputDialog.title);
        btnRight.setText(xInputDialog.rightBtnTxt);
        btnRight.setTextColor(xInputDialog.rightBtnTxtColor);
        btnLeft.setText(xInputDialog.leftBtnTxt);
        btnLeft.setTextColor(xInputDialog.leftBtnTxtColor);
        tvContent.setText(xInputDialog.message);

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnClickListener != null)
                    btnClickListener.rightBtnClick();
                dismiss();

            }
        });
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnClickListener != null)
                    btnClickListener.leftBtnClick();
                dismiss();
            }
        });
    }

    public static class Builder {
        private XTipDialog xInputDialog;

        public Builder(Context context) {
            xInputDialog = new XTipDialog(context);
        }

        public Builder title(String title) {
            xInputDialog.title = title;
            return this;
        }

        /**
         * 左侧按钮文字
         *
         * @param leftBtnTxt
         * @return
         */
        public Builder leftBtnTxt(String leftBtnTxt) {
            xInputDialog.leftBtnTxt = leftBtnTxt;
            return this;
        }

        /**
         * 右侧按钮文字
         *
         * @param rightBtnTxt
         * @return
         */
        public Builder rightBtnTxt(String rightBtnTxt) {
            xInputDialog.rightBtnTxt = rightBtnTxt;
            return this;
        }

        /**
         * 背景是否半透明
         *
         * @return
         */
        public Builder setBgTransparent(boolean b) {
            xInputDialog.isBgTrsnsparent = b;
            return this;
        }

        /**
         * 左边按钮颜色
         *
         * @param leftBtnTxtColor
         * @return
         */
        public Builder leftBtnTxtColor(int leftBtnTxtColor) {
            xInputDialog.leftBtnTxtColor = leftBtnTxtColor;
            return this;
        }

        /**
         * 右边按钮颜色
         *
         * @param rightBtnTxtColor
         * @return
         */
        public Builder rightBtnTxtColor(int rightBtnTxtColor) {
            xInputDialog.rightBtnTxtColor = rightBtnTxtColor;
            return this;
        }

        /**
         * 确定点击
         *
         * @param listener
         * @return
         */
        public Builder setSureClickListener(BtnClickListener listener) {
            xInputDialog.btnClickListener = listener;
            return this;
        }

        /**
         * 设置该对话框能否被Cancel掉，默认可以
         *
         * @param cancelable
         */
        public Builder setCancelable(boolean cancelable) {
            xInputDialog.setCancelable(cancelable);
            return this;
        }

        /**
         * 设置提示
         *
         * @param msg
         */
        public Builder setMessage(String msg) {
            xInputDialog.message = msg;
            return this;
        }

        /**
         * 通过Builder类设置完属性后构造对话框的方法
         */
        public XTipDialog create() {
            Window window = xInputDialog.getWindow();
            window.setGravity(Gravity.CENTER); //可设置dialog的位置
            window.getDecorView().setPadding(0, 0, 0, 0); //消除边距
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;   //设置宽度充满屏幕
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            if (xInputDialog.isBgTrsnsparent) {
                lp.dimAmount = 0f;
            }
            window.setAttributes(lp);
            return xInputDialog;
        }
    }
}
