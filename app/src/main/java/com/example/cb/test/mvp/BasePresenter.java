package com.example.cb.test.mvp;

public abstract class BasePresenter<V> {

    protected V mView;

    public BasePresenter(V view) {
        attachView(view);
    }

    /**
     * p层与view层绑定{@link BasePresenter 的构造方法}
     *
     * @param view
     */
    public void attachView(V view) {
        mView = view;
    }


    public void detachView() {
        mView = null;
    }
}