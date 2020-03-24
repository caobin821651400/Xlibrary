package com.example.cb.test.rx.rxjava;

import io.reactivex.disposables.Disposable;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2020/3/24 10:01
 * @Desc :自定义泛型Observer
 * ====================================================
 */
public interface IObserver<T> {

    public void doOnSubscribe(Disposable d);

    public void doOnNext(T t);

    public void doOnError(String msg);

    public void doOnComplete();

}
