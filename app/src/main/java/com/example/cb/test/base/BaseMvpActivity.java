package com.example.cb.test.base;

import android.os.Bundle;

/**
 * MVP模式activity的基类
 *
 * @author bin
 * @date 2018/12/25.
 */
public abstract class BaseMvpActivity<T extends BasePresenter> extends BaseActivity {
    protected T mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = createPresenter();
        setContentView(getContentViewId());
        initView();
        initData();
        initListener();
    }

    /**
     * 子类返回presenter
     *
     * @return
     */
    protected abstract T createPresenter();

    /**
     * 返回布局ID
     *
     * @return
     */
    protected abstract int getContentViewId();

    /**
     * 一般做view的初始化
     */
    public void initView() {
    }

    /**
     * 数据初始化操作
     */
    public void initData() {
    }

    /**
     * 事件监听操作
     */
    public void initListener() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //及时销毁mvp的view
        if (mPresenter != null)
            mPresenter.detachView();
    }
}
