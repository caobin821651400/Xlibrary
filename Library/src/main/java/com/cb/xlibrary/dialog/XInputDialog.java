package com.cb.xlibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cb.xlibrary.R;

/**
 * author : caobin
 * e-mail : 821651400@qq.com
 * time   : 2017/12/15
 * desc   :输入框弹窗
 */
public class XInputDialog extends Dialog {

    public interface InputDialogBtnClickListener {
        void onClick(String content);
    }

    private Button btnSave;
    private Button btnCancel;
    private EditText etContent;
    private TextView tvTitle;
    private InputDialogBtnClickListener inputDialogBtnClickListener;

    private String title;
    private String leftBtnTxt = "";
    private String rightBtnTxt = "";
    private Drawable editBg;
    private boolean isBgTrsnsparent = false;//背景半透明
    private int leftBtnTxtColor = Color.parseColor("#000000");
    private int rightBtnTxtColor = Color.parseColor("#FAC200");


    private XInputDialog(@NonNull Context context) {
        super(context, R.style.XDialog);
        editBg = ContextCompat.getDrawable(context, R.drawable.shape_input_et_bg);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_dialog);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        etContent = (EditText) findViewById(R.id.et_opinion);
    }

    @Override
    public void show() {
        super.show();
        show(this);
    }

    /**
     * @param xInputDialog
     */
    private void show(XInputDialog xInputDialog) {
        tvTitle.setText(xInputDialog.title);
        btnSave.setText(xInputDialog.rightBtnTxt);
        btnSave.setTextColor(xInputDialog.rightBtnTxtColor);
        btnCancel.setText(xInputDialog.leftBtnTxt);
        btnCancel.setTextColor(xInputDialog.leftBtnTxtColor);
        etContent.setBackgroundDrawable(editBg);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputDialogBtnClickListener != null)
                    inputDialogBtnClickListener.onClick(etContent.getText().toString().trim());
                cancel();

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }

    public static class Builder {
        private XInputDialog xInputDialog;

        public Builder(Context context) {
            xInputDialog = new XInputDialog(context);
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
         * edit背景
         *
         * @param editBg
         * @return
         */
        public Builder strokeColor(Drawable editBg) {
            xInputDialog.editBg = editBg;
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
        public Builder setSureClickListener(InputDialogBtnClickListener listener) {
            xInputDialog.inputDialogBtnClickListener = listener;
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
         * 通过Builder类设置完属性后构造对话框的方法
         */
        public XInputDialog create() {
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
