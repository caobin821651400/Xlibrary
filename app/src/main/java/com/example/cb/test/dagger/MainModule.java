package com.example.cb.test.dagger;

import dagger.Module;
import dagger.Provides;

/**
 * Created by bin on 2019/1/21.
 */
@Module//第一步生成Module
public class MainModule {
    //第二步 使用provider 注解实例化对象
    @Provides
    AObject providerA(BObject b) {
        return new AObject(b);
    }

    @Provides
    BObject providerB() {
        return new BObject();
    }
}
