package com.example.cb.test.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2019/9/5 16:32
 * @Desc :MVP 基类，集成它必须重写createPresenter返回一个Presenter对象
 * ====================================================
 */
public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity implements IBaseView {

    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        super.onCreate(savedInstanceState);
    }

    /**
     * 不需要用MVP模式返回NULL即可
     *
     * @return
     */
    protected abstract P createPresenter();

    @Override
    public void showDialog() {
        showDLG();
    }

    @Override
    public void hideDialog() {
        disMissDLG();
    }

    @Override
    public void showTip(String msg) {
        showAlert(msg);
    }

    @Override
    public void showToast(String msg) {
        toast(msg);
    }

    @Override
    public void showErrorToast(String msg) {
        toastError(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }
}
