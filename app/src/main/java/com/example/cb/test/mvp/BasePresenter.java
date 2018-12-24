package com.example.cb.test.mvp;

public abstract class BasePresenter<V> {

    protected V mView;

    public BasePresenter(V view) {
        attachView(view);
    }

    public void attachView(V view) {
        mView = view;
    }

    public void detachView() {
        mView = null;
    }
}