package com.example.cb.test.mvp;

import android.view.View;

import com.example.cb.test.BaseFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by bin on 2018/12/25.
 */

public abstract class BaseMvpFragment<T extends BasePresenter> extends BaseFragment {

    protected T mPresenter;
    private Unbinder unbinder;

    @Override
    public int getRootViewId() {
        return getContentViewId();
    }

    @Override
    public void initUI(View v) {
        mPresenter = createPresenter();
        unbinder = ButterKnife.bind(this, v);
        initView(v);
        initData();
        initListener();
    }

    protected abstract T createPresenter();

    public abstract int getContentViewId();

    public abstract void initData();

    public abstract void initListener();

    public abstract void initView(View v);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null)
            unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }
}
