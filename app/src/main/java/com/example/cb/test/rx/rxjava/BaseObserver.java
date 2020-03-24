package com.example.cb.test.rx.rxjava;

import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T> extends XObserver<T> {


    protected abstract void onSuccess(T t);

    protected abstract void onError(String msg);

    @Override
    public void doOnSubscribe(Disposable d) {

    }

    @Override
    public void doOnNext(T t) {
        onSuccess(t);
    }

    @Override
    public void doOnError(String msg) {
        onError(msg);
    }

    @Override
    public void doOnComplete() {

    }
}
