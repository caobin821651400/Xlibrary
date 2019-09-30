package com.example.cb.test.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2019/9/5 16:32
 * @Desc :MVP 基类，集成它必须重写createPresenter返回一个Presenter对象
 * ====================================================
 */
public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment implements IBaseView {

    protected P mPresenter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = createPresenter();
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
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }
}
