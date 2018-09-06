package com.example.cb.test.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.cb.xlibrary.utils.XLogUtils;
import com.example.cb.test.BaseActivity;
import com.example.cb.test.R;

public class LayoutTestActivity extends BaseActivity {


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_test);

        Button button = findViewById(R.id.button);

        /**
         * 顺序 onTouch-->onTouchEvent-->onClick
         */

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                XLogUtils.v("view onClick");
            }
        });

        /**
         * 返回true 则onTouchEvent和onClickListener都不执行
         *
         * 返回false 则时间继续向下传递 onTouchEvent和onClickListener都可以执行
         */
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                XLogUtils.v("view onTouch");
                return false;
            }
        });
    }
}
