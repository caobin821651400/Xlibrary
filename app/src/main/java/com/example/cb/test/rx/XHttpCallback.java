package com.example.cb.test.rx;

/**
 * author : caobin
 * e-mail : 821651400@qq.com
 * time   : 2018/01/18
 * desc   :请求回调
 */
public interface XHttpCallback<T> {
    // 网络请求成功
    void onSuccess(T t);

    // 网络请求失败
    void onError(String error);
}
