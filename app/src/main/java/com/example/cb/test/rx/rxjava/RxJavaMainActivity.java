package com.example.cb.test.rx.rxjava;

import android.content.Intent;
import android.view.View;

import com.example.cb.test.R;
import com.example.cb.test.base.BaseActivity;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2020/8/11 11:40
 * @Desc :RxJava
 * ====================================================
 */
public class RxJavaMainActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_rx_java_main;
    }

    @Override
    protected void initUI() {
        setHeaderTitle("RxJava");
    }

    @Override
    protected void initEvent() {

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

    /**
     * 功能操作符
     *
     * @param view
     */
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
