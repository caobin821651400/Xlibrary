package com.example.cb.test.rx.rxjava;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2020/3/24 10:04
 * @Desc :
 * ====================================================
 */
public abstract class XObserver<T> implements Observer<T>, IObserver<T> {


    @Override
    public void onSubscribe(Disposable d) {
        doOnSubscribe(d);
    }

    @Override
    public void onNext(T t) {
        doOnNext(t);
    }

    @Override
    public void onError(Throwable e) {
        doOnError(e.getMessage());
    }

    @Override
    public void onComplete() {
        doOnComplete();
    }
}
