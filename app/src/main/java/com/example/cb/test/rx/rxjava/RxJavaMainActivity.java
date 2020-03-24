package com.example.cb.test.rx.rxjava;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cb.test.R;

public class RxJavaMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_main);
    }

    /**
     * 基本的使用
     *
     * @param view
     */
    public void simpleUse(View view) {
        startActivity(new Intent(this, SimpleUseActivity.class));
    }

    /**
     * 操作符
     *
     * @param view
     */
    public void simpleOperator(View view) {
        startActivity(new Intent(this, RxBaseOperatorActivity.class));
    }

    /**
     * 变换操作符
     *
     * @param view
     */
    public void changeOperator(View view) {
        startActivity(new Intent(this, RxChangeOperatorActivity.class));
    }

    /**
     * 组合&合并操作符
     *
     * @param view
     */
    public void mergeOperator(View view) {
        startActivity(new Intent(this, RxMergeActivity.class));
    }

    public void functionOperator(View view) {
        startActivity(new Intent(this, RxFunctionActivity.class));
    }

    /**
     * 背压
     *
     * @param view
     */
    public void BackPressure(View view) {
        startActivity(new Intent(this, RxBackPressureActivity.class));
    }

    /**
     * 条件操作符
     *
     * @param view
     */
    public void booleanOperator(View view) {
        startActivity(new Intent(this, RxBooleanActivity.class));
    }

    /**
     * 条件操作符
     *
     * @param view
     */
    public void filterOperator(View view) {
        startActivity(new Intent(this, RxFilterActivity.class));
    }
}
