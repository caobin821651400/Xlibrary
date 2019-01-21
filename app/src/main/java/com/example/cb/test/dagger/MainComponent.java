package com.example.cb.test.dagger;

import dagger.Component;

/**
 * 康跑能
 * Created by bin on 2019/1/21.
 * 第一步 添加@Component
 * 第二部 添加module
 */
@Component(modules = {MainModule.class})
public interface MainComponent {
    //第三步 绑定activity
    void inject(DaggerTestActivity activity);
}
