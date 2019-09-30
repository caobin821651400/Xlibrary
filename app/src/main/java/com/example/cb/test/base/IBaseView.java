package com.example.cb.test.base;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2019/9/6 15:14
 * @Desc :定义一些基本View的操作,
 * 方便在P层调用{@link BaseActivity}and{@link BaseFragment}中的方法
 * ====================================================
 */
public interface IBaseView {

    void showDialog();

    void hideDialog();

    void showTip(String msg);

    void showToast(String msg);

    void showErrorToast(String msg);
}
